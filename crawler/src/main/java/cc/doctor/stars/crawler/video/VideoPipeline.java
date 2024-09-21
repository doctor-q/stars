package cc.doctor.stars.crawler.video;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.mapper.FileMapper;
import cc.doctor.stars.biz.mapper.VideoTaskMapper;
import cc.doctor.stars.biz.model.File;
import cc.doctor.stars.biz.model.VideoTask;
import cc.doctor.stars.biz.store.StoreFactory;
import cc.doctor.stars.crawler.enums.TaskStatus;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 视频文字提取
 */
@Service
@Slf4j
public class VideoPipeline {

    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Autowired
    private VideoTaskMapper taskMapper;

    @Autowired
    private FileMapper fileMapper;

    public void run(boolean failed) {
        List<VideoTask> videoTasks = taskMapper.selectList(new LambdaQueryWrapper<VideoTask>()
                .in(VideoTask::getTaskStatus, failed ? Arrays.asList(TaskStatus.NEW.getStatus(), TaskStatus.FAILED.getStatus()) :
                        Collections.singleton(TaskStatus.NEW.getStatus())));
        for (VideoTask videoTask : videoTasks) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Integer uploadId = videoTask.getUploadId();
                    Integer frameId = videoTask.getFrameId();
                    VideoTask update = new VideoTask();
                    update.setId(videoTask.getId());
                    try {
                        if (videoTask.getUpload() == YesOrNoEnum.NO.getValue()) {
                            uploadId = upload(videoTask.getOriginUrl());
                            update.setUpload(YesOrNoEnum.YES.getValue());
                            update.setUploadId(uploadId);
                            taskMapper.updateById(update);
                        }
                        if (videoTask.getFramed() == YesOrNoEnum.NO.getValue()) {
                            frameId = frame(videoTask.getOriginUrl());
                            update.setFramed(YesOrNoEnum.YES.getValue());
                            update.setFrameId(frameId);
                            taskMapper.updateById(update);
                        }
                        if (videoTask.getExtractSubTitle() == YesOrNoEnum.NO.getValue()) {
                            String subTitle = extractSubTitle(frameId);
                            update.setExtractSubTitle(YesOrNoEnum.YES.getValue());
                            update.setExtractSubTitleText(subTitle);
                            taskMapper.updateById(update);
                        }
                        update.setTaskStatus(TaskStatus.SUCCESS.getStatus());
                        taskMapper.updateById(update);
                    } catch (Exception e) {
                        log.error("视频处理出错", e);
                        update.setErrorStack(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
                        update.setTaskStatus(TaskStatus.FAILED.getStatus());
                        taskMapper.updateById(update);
                    }
                }
            });
        }
    }

    private InputStream getInputStream(String originUrl) throws IOException {
        URL url = new URL(originUrl);
        URLConnection urlConnection = url.openConnection();
        return urlConnection.getInputStream();
    }

    public Integer upload(String originUrl) throws IOException {
        String fileName = UUID.randomUUID().toString();
        File file = new File();
        file.setPub(YesOrNoEnum.YES.getValue());
        file.setFileName(fileName);
        file.setStoreType(StoreFactory.defaultStoreType());
        file.setPath(fileName);
        fileMapper.insert(file);
        InputStream inputStream = getInputStream(originUrl);
        StoreFactory.upload(file, inputStream);
        return file.getId();
    }

    public Integer frame(String originUrl) throws IOException {
        String path = UUID.randomUUID().toString();
        File dir = new File();
        dir.setPub(YesOrNoEnum.YES.getValue());
        dir.setFileName(path);
        dir.setStoreType(StoreFactory.defaultStoreType());
        dir.setPath(path);
        dir.setIsDir(YesOrNoEnum.YES.getValue());
        fileMapper.insert(dir);

        List<File> files = new ArrayList<>();
        InputStream inputStream = getInputStream(originUrl);
        VideoFramer.video2Frames(inputStream, s -> {
            File file = new File();
            file.setPub(YesOrNoEnum.YES.getValue());
            file.setFileName(s);
            file.setStoreType(StoreFactory.defaultStoreType());
            file.setPath(path + "/" + s);
            file.setDirId(dir.getId());
            try {
                OutputStream outputStream = StoreFactory.createOutputStream(file);
                files.add(file);
                return outputStream;
            } catch (IOException e) {
                log.error("输出流创建失败", e);
                return null;
            }
        });
        if (!files.isEmpty()) {
            fileMapper.insertBatch(files);
        }
        return dir.getId();
    }

    public String extractSubTitle(Integer dirId) throws IOException, InterruptedException {
        // 创建临时目录
        List<File> files = fileMapper.selectList(new LambdaQueryWrapper<File>().eq(File::getDirId, dirId));
        if (files.isEmpty()) {
            return null;
        }
        String tmpDir = UUID.randomUUID().toString();
        Path directory = Files.createTempDirectory(tmpDir);
        for (File file : files) {
            InputStream inputStream = StoreFactory.load(file);
            Path path = Files.createFile(Paths.get(directory.toString(), file.getFileName()));
            Files.write(path, StreamUtils.copyToByteArray(inputStream));
        }
        List<String> subTitles = SubTitleExtractor.extract(directory.toString());
        return JSON.toJSONString(subTitles);
    }
}
