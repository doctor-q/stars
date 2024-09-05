package cc.doctor.stars.crawler.video;


import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.bytedeco.opencv.opencv_core.Mat;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_core.bitwise_not;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

/**
 * 图片文字提取
 */
@Slf4j
public class SubTitleExtractor {
    /**
     * 二值化
     *
     * @param oriImg    输入图片
     * @param outputImg 输出图片
     */
    public static void binarization(String oriImg, String outputImg) {
        Mat img = imread(oriImg);
        // 色彩空间转换，RGB转灰色
        cvtColor(img, img, COLOR_RGB2GRAY);
        // 二值化
        threshold(img, img, 240, 255, THRESH_BINARY);
        // 反转像素
        bitwise_not(img, img);
        // adaptiveThreshold(img, img, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 25, 10);
        imwrite(outputImg, img);
    }

    public List<String> extractFromPath(String dir) throws IOException {
        List<String> extracts = new ArrayList<>();
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
        tesseract.setLanguage("chi_sim");
        Files.list(Paths.get(dir)).forEach(file -> {
            try {
                String binFilePath = file.getParent().toString() + "/bin-" + file.getFileName().toString();
                binarization(file.toString(), binFilePath);
                Rectangle rect = new Rectangle(0, 460, 576, 100);
                String binOcr = tesseract.doOCR(new File(binFilePath), rect);
                System.out.println(binOcr);
//                extracts.add(ocr);
            } catch (TesseractException e) {
                log.error("文件{} OCR提取错误", file.toString(), e);
            }
        });
        return extracts;
    }

    public static void main(String[] args) throws IOException {
        List<String> strings = new SubTitleExtractor().extractFromPath("/home/doctor/Pictures/frames/test");
        System.out.println(strings);
    }
}
