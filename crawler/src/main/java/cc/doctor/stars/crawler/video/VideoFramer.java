package cc.doctor.stars.crawler.video;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

/**
 * 视频转帧
 */
@Slf4j
public class VideoFramer {

    /**
     * 视频转帧图片，按照每秒1个
     *
     * @param videoPath 视频地址
     * @return 图片地址
     */
    public static String video2Frames(String videoPath) throws IOException {
        Path path = Paths.get(videoPath);
        String fileName = path.getFileName().toString();

        String dir = path.getParent().toString();
        String framePath = dir + "/frames/" + fileName;
        File file = new File(framePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        video2Frames(new FileInputStream(videoPath), new Function<String, OutputStream>() {
            @Override
            public OutputStream apply(String s) {
                try {
                    return new FileOutputStream(new File(framePath, s));
                } catch (FileNotFoundException e) {
                    log.error("", e);
                    return null;
                }
            }
        });
        return framePath;
    }

    public static void video2Frames(InputStream inputStream, Function<String, OutputStream> outputStreamFunction) throws IOException {
        // 创建FFmpeg帧抓取器
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        grabber.start();

        // 获取视频总帧数
        int lengthInFrames = grabber.getLengthInFrames();
        // 创建转换器
        Java2DFrameConverter converter = new Java2DFrameConverter();
        int frameRate = (int) grabber.getFrameRate();

        boolean grab = false;
        // 循环抓取帧
        for (int i = 0; i < lengthInFrames; i++) {
            // 抓取帧
            Frame frame = grabber.grabFrame();
            // 按帧率保存一次
            if (i % frameRate == 0) {
                grab = true;
            }
            if (frame != null && frame.image != null && grab) {
                // 将帧转换为BufferedImage
                BufferedImage bufferedImage = converter.convert(frame);
                if (bufferedImage != null) {
                    String imageName = String.format("%05d.jpg", i);
                    // 将帧保存为图片
                    OutputStream outputStream = outputStreamFunction.apply(imageName);
                    if (outputStream != null) {
                        ImageIO.write(bufferedImage, "jpg", outputStream);
                        grab = false;
                    }
                }
            }
        }

        // 停止抓取器
        grabber.stop();
    }

    public static void main(String[] args) throws IOException {
//        video2Frames("/tmp/d4dafdc6-1012-4146-9be1-dcb5a327e857");
        URL url = new URL("https://v2zezm5tmmmjfb69jh5xjv5cg11etopys.free-lbv3.idouyinvod.com/v83-016.douyinvod.com/a9e50e1db554358837d2be797edab741/66ed8b7f/video/tos/cn/tos-cn-ve-15/093b4c75b123435bae620ffd3dd5f785/?a=1128&ch=0&cr=0&dr=0&er=0&cd=0%7C0%7C0%7C0&cv=1&br=885&bt=885&cs=0&ds=3&ft=VWi6g76qRR0syrC3-Dv2Nc0iPMgzbLM.-2sU_4FTXgJJNv7TGW&mime_type=video_mp4&qs=0&rc=NjRmPDU1NThkOWVnZzs6aUBpajs5aWo3eWg5eTMzO2kzM0A2Y182NjBfXl8xYF4uLi0xYSNmMG1nNC40aDFfLS1fLTBzcw%3D%3D&btag=c0000e00088000&cc=4c&cquery=100y&dy_q=1726840164&l=202409202");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        video2Frames(inputStream, new Function<String, OutputStream>() {
            @Override
            public OutputStream apply(String s) {
                try {
                    return new FileOutputStream("/tmp/111/" + s);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
    }

}
