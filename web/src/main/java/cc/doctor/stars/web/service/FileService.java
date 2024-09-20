package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.FileMapper;
import cc.doctor.stars.biz.model.File;
import cc.doctor.stars.biz.store.StoreFactory;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private RequestContext requestContext;

    public Response<Integer> upload(MultipartFile file) throws IOException {
        Integer fileId = storeMultipart(file);
        return Response.success(fileId);
    }

    public void download(Integer fileId, HttpServletResponse response) throws BusinessException, IOException {
        File file = fileMapper.selectById(fileId);
        if (file == null || !file.getUserId().equals(requestContext.getUserId())) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "文件错误");
        }
        InputStream inputStream = StoreFactory.load(file);
        byte[] bytes = StreamUtils.copyToByteArray(inputStream);
        response.getOutputStream().write(bytes);
    }

    private File multipartToFile(MultipartFile multipartFile) {
        File file = new File();
        file.setPub(YesOrNoEnum.NO.getValue());
        file.setUserId(requestContext.getUserId());
        file.setFileName(multipartFile.getOriginalFilename());
        file.setStoreType(StoreFactory.defaultStoreType());
        file.setPath(UUID.randomUUID().toString());
        return file;
    }

    public Integer storeMultipart(MultipartFile multipartFile) throws IOException {
        File file = multipartToFile(multipartFile);
        fileMapper.insert(file);
        StoreFactory.upload(file, multipartFile.getInputStream());
        return file.getId();
    }

    public List<Integer> storeMultiparts(MultipartFile[] multipartFiles) throws IOException {
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            File file = multipartToFile(multipartFile);
            files.add(file);
            StoreFactory.upload(file, multipartFile.getInputStream());
        }
        fileMapper.insertBatch(files);
        return files.stream().map(File::getId).collect(Collectors.toList());
    }
}
