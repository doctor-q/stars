package cc.doctor.stars.crawler.utils;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;

import java.util.List;
import java.util.stream.Collectors;

public class OcrUtils {

    private static final float score_threshold = 0.6f;
    private static InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);

    public static String getResult(String path) {
        OcrResult ocrResult = engine.runOcr(path);
        // 过滤分数小于0.6的块
        List<TextBlock> textBlocks = ocrResult.getTextBlocks().stream().filter(tb -> avg(tb.getCharScores()) > score_threshold).collect(Collectors.toList());
        return textBlocks.stream().map(TextBlock::getText).collect(Collectors.joining("\n"));
    }

    private static float avg(float[] scores) {
        float sum = 0;
        for (float score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }
}
