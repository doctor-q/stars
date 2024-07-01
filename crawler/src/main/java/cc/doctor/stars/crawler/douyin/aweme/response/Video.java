package cc.doctor.stars.crawler.douyin.aweme.response;

import lombok.Data;

import java.util.List;

@Data
public class Video {

    private int height;

    private int width;
    //播放地址
    private Play_addr play_addr;
    //封面
    private Cover cover;
    //封面
    private Dynamic_cover dynamic_cover;
    //封面
    private Origin_cover origin_cover;
    //封面
    private Gaussian_cover gaussian_cover;
    //动态封面
    private Animated_cover animated_cover;

    private boolean use_static_cover;
    // 比率 540p
    private String ratio;

    private String bit_rate_audio;

    private List<String> big_thumbs;

    private List<Bit_rate> bit_rate;

    private int duration;

    private Audio audio;

    private int is_source_HDR;

    private Play_addr_h264 play_addr_h264;
    //格式 mp4
    private String format;

    private String meta;

    private String misc_download_addrs;

    private String video_model;


    @Data
    public static class Play_addr {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;

        private String url_key;

        private int data_size;

        private String file_hash;

        private String file_cs;
    }

    @Data
    public static class Play_addr_h264 {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;

        private String url_key;

        private int data_size;

        private String file_hash;

        private String file_cs;
    }

    @Data
    public static class Cover {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Dynamic_cover {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Origin_cover {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;

    }

    @Data
    public static class Gaussian_cover {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Animated_cover {
        private String uri;

        private List<String> url_list;
    }

    @Data
    public static class Bit_rate {
        private String gear_name;

        private int quality_type;

        private int bit_rate;

        private Play_addr play_addr;

        private int is_h265;

        private int is_bytevc1;

        private String HDR_type;

        private String HDR_bit;

        private int FPS;

        private String video_extra;

        private String format;
    }

    @Data
    public static class Audio {
        private String original_sound_infos;
    }
}
