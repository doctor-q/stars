package cc.doctor.stars.crawler.utils;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OcrUtils {

    private static final float score_threshold = 0.6f;
    private static InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);

    public static List<TextBlock> getResult(String path) {
        OcrResult ocrResult = engine.runOcr(path);
        // 过滤分数小于0.6的块
        return ocrResult.getTextBlocks().stream().filter(tb -> avg(tb) > score_threshold).collect(Collectors.toList());
    }

    public static float avg(TextBlock textBlock) {
        float sum = 0;
        float[] scores = textBlock.getCharScores();
        for (float score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }

    public static float avg(Collection<TextBlock> textBlocks) {
        float sum = 0;
        int num = 0;
        for (TextBlock textBlock : textBlocks) {
            num += textBlock.getCharScores().length;
            for (float score : textBlock.getCharScores()) {
                sum += score;
            }
        }
        return sum / num;
    }

    public static String join(List<TextBlock> textBlocks, String split) {
        return textBlocks.stream().map(TextBlock::getText).collect(Collectors.joining(split));
    }

    public static void main(String[] args) {
        List<TextBlock> result = getResult("/home/doctor/Pictures/frames/by.mp4/03975.jpg");
        System.out.println(join(result, "\n"));
        System.out.println(avg(result));
    }
}
