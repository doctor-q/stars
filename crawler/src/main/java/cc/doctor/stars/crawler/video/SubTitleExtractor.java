package cc.doctor.stars.crawler.video;


import cc.doctor.stars.biz.utils.ConcurrentUtils;
import cc.doctor.stars.crawler.utils.OcrUtils;
import com.benjaminwan.ocrlibrary.TextBlock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 图片文字提取
 */
@Slf4j
public class SubTitleExtractor {

    private static final double similarity_threshold = 0.85;

    public static List<String> extract(String dir) throws IOException, InterruptedException {
        return removeDup(extractFromPath(dir));
    }

    /**
     * 优化：
     * 1. 支持对文字进行范围筛选
     */
    public static Map<String, List<TextBlock>> extractFromPath(String dir) throws IOException, InterruptedException {
        Map<String, List<TextBlock>> extracts = new TreeMap<>();
        List<Runnable> runnables = new ArrayList<>();
        Files.list(Paths.get(dir)).map(Path::toString).sorted().forEach(file -> {
            runnables.add(() -> {
                List<TextBlock> result = OcrUtils.getResult(file);
                if (CollectionUtils.isEmpty(result)) {
                    log.warn("文件{}没有字幕或识别质量太低", file);
                } else {
                    extracts.put(file, result);
                }
            });
        });
        if (runnables.isEmpty()) {
            return Collections.emptyMap();
        }
        runnables.get(0).run();
        ConcurrentUtils.newParallelPool(8, runnables);
        return extracts;
    }

    /**
     * 去重、去标题等
     * 去重：根据编辑距离以一个阈值判断是否相同字幕，取分值更高的文本作为解析文本
     */
    public static List<String> removeDup(Map<String, List<TextBlock>> extracts) {
        List<List<TextBlock>> results = new ArrayList<>();
        for (List<TextBlock> textBlocks : extracts.values()) {
            if (results.isEmpty()) {
                results.add(textBlocks);
            } else {
                List<TextBlock> prevBlocks = results.get(results.size() - 1);
                double similarity = similarity(prevBlocks, textBlocks);
                if (similarity >= similarity_threshold) {
                    if (OcrUtils.avg(prevBlocks) < OcrUtils.avg(textBlocks)) {
                        results.set(results.size() - 1, textBlocks);
                    }
                } else {
                    results.add(textBlocks);
                }
            }
        }
        // 去标题
        return results.stream().map(r -> OcrUtils.join(r, ",")).collect(Collectors.toList());
    }

    private static double similarity(List<TextBlock> textBlocks1, List<TextBlock> textBlocks2) {
        String str1 = OcrUtils.join(textBlocks1, "");
        String str2 = OcrUtils.join(textBlocks2, "");
        int editDistance = LevenshteinDistance.getDefaultInstance().apply(str1, str2);
        return 1 - ((double) editDistance / Math.max(str1.length(), str2.length()));
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> strings = extract("/home/doctor/Pictures/frames/by.mp4");
        System.out.println(strings);
    }

}
