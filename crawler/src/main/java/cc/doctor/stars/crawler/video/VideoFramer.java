package cc.doctor.stars.crawler.video;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 视频转帧
 */
public class VideoFramer {

    /**
     * 视频转帧图片，按照每秒1个
     *
     * @param videoPath 视频地址
     * @return 图片地址
     */
    public String video2Frames(String videoPath) throws IOException {
        Path path = Paths.get(videoPath);
        String dir = path.getParent().toString();
        String fileName = path.getFileName().toString();
        String framePath = dir + "/frames/" + fileName;
        File file = new File(framePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 创建FFmpeg帧抓取器
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
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
                    // 将帧保存为图片
                    ImageIO.write(bufferedImage, "jpg", new File(framePath, String.format("%05d.jpg", i)));
                    grab = false;
                }
            }
        }

        // 停止抓取器
        grabber.stop();
        return framePath;
    }

    public static void main(String[] args) throws IOException {
        new VideoFramer().video2Frames("/home/doctor/Pictures/8j.mp4");
    }

}
