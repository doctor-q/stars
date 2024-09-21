package cc.doctor.stars.crawler.video;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
        // 循环抓取帧
        for (int i = 0; i < lengthInFrames; i++) {
            Frame frame = grabber.grabImage();
            if (frame == null) {
                break;
            }
            // 抓取帧,跳过音频帧
            if (frame.image == null) {
                i--;
                continue;
            }
            // 按帧率保存一次
            if (i % frameRate == 0) {
                // 将帧转换为BufferedImage
                BufferedImage bufferedImage = converter.convert(frame);
                if (bufferedImage != null) {
                    String imageName = String.format("%05d.jpg", i);
                    // 将帧保存为图片
                    OutputStream outputStream = outputStreamFunction.apply(imageName);
                    if (outputStream != null) {
                        ImageIO.write(bufferedImage, "jpg", outputStream);
                    }
                }
            }
        }

        // 停止抓取器
        grabber.stop();
    }

    public static void main(String[] args) throws IOException {
        video2Frames("/home/doctor/Pictures/by.mp4");
    }

}
