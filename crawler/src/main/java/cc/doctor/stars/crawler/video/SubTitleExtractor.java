package cc.doctor.stars.crawler.video;


import cc.doctor.stars.crawler.utils.OcrUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片文字提取
 */
@Slf4j
public class SubTitleExtractor {

    public static List<String> extract(String dir) throws IOException {
        return removeDup(extractFromPath(dir));
    }

    public static List<String> extractFromPath(String dir) throws IOException {
        List<String> extracts = new ArrayList<>();
        Files.list(Paths.get(dir)).map(Path::toString).sorted().forEach(file -> {
            String result = OcrUtils.getResult(file);
            if (StringUtils.isEmpty(result)) {
                log.warn("文件{}识别质量太低", file);
            } else {
                extracts.add(result);
            }
        });
        return extracts;
    }

    /**
     * 去重、去标题等
     * 去重：根据编辑距离以一个阈值判断是否相同字幕，取分值更高的文本作为解析文本
     */
    public static List<String> removeDup(List<String> extracts) {
        List<String> results = new ArrayList<>();
        for (String extract : extracts) {
            if (results.isEmpty()) {
                results.add(extract);
            } else {
                String prev = results.get(results.size() - 1);
                if (!extract.equals(prev)) {
                    results.add(extract);
                }
            }
        }
        // 去标题
        return results;
    }

    public static void main(String[] args) throws IOException {
        List<String> strings = extractFromPath("/home/doctor/Pictures/frames/lyf.mp4");
        System.out.println(strings);
        List<String> strings1 = removeDup(strings);
        System.out.println(strings1);
//        String str1 = "接着朱刚鬣原形毕露, 甜兮";
//        String str2 = "接着朱刚鬣原形毕露, 甜说居";
//        int editDistance = LevenshteinDistance.getDefaultInstance().apply(str1, str2);
//        double similarity = 1 - ((double) editDistance / Math.max(str1.length(), str2.length()));
//
//        System.out.println("commons-text 包：Edit Distance: " + editDistance);
//        System.out.println("commons-text 包：Similarity: " + similarity);
    }

}
