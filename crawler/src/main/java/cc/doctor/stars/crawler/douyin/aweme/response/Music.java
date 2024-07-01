package cc.doctor.stars.crawler.douyin.aweme.response;

import lombok.Data;

import java.util.List;

@Data
public class Music {
    private long id;

    private String id_str;

    private String title;

    private String author;

    private String album;

    private Cover_hd cover_hd;

    private Cover_large cover_large;

    private Cover_medium cover_medium;

    private Cover_thumb cover_thumb;

    private Play_url play_url;

    private String schema_url;

    private int source_platform;

    private int start_time;

    private int end_time;

    private int duration;

    private String extra;

    private int user_count;

    private String position;

    private int collect_stat;

    private int status;

    private String offline_desc;

    private String owner_id;

    private String owner_nickname;

    private boolean is_original;

    private String mid;

    private int binded_challenge_id;

    private boolean redirect;

    private boolean is_restricted;

    private boolean author_deleted;

    private boolean is_del_video;

    private boolean is_video_self_see;

    private String owner_handle;

    private String author_position;

    private boolean prevent_download;

    private Strong_beat_url strong_beat_url;

    private String unshelve_countries;

    private int prevent_item_download_status;

    private List<String> external_song_info;

    private String sec_uid;

    private Avatar_thumb avatar_thumb;

    private Avatar_medium avatar_medium;

    private Avatar_large avatar_large;

    private double preview_start_time;

    private int preview_end_time;

    private boolean is_commerce_music;

    private boolean is_original_sound;

    private int audition_duration;

    private int shoot_duration;

    private int reason_type;

    private List<Artists> artists;

    private String lyric_short_position;

    private boolean mute_share;

    private String tag_list;

    private boolean dmv_auto_show;

    private boolean is_pgc;

    private boolean is_matched_metadata;

    private boolean is_audio_url_with_cookie;

    private String music_chart_ranks;

    private boolean can_background_play;

    private int music_status;

    private int video_duration;

    private int pgc_music_type;

    private Cover_color_hsv cover_color_hsv;

    private int author_status;

    private Search_impr search_impr;

    private Song song;

    private String artist_user_infos;

    private int dsp_status;

    private String musician_user_infos;

    private int music_collect_count;

    private String music_cover_atmosphere_color_value;

    @Data
    public static class Cover_hd {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Cover_large {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Cover_medium {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Cover_thumb {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Play_url {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;

        private String url_key;
    }

    @Data
    public static class Strong_beat_url {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Avatar_medium {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Avatar_large {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }


    @Data
    public static class Avatar_thumb {
        private String uri;

        private List<String> url_list;

        private int width;

        private int height;
    }

    @Data
    public static class Avatar {
        private String uri;

        private List<String> url_list;
    }

    @Data
    public static class Artists {
        private String uid;

        private String sec_uid;

        private String nick_name;

        private String handle;

        private Avatar avatar;

        private boolean is_verified;

        private int enter_type;
    }

    @Data
    public static class Cover_color_hsv {
        private int h;

        private int s;

        private int v;
    }

    @Data
    public static class Search_impr {
        private String entity_id;
    }

    @Data
    public static class Chorus {
        private int start_ms;

        private int duration_ms;
    }

    @Data
    public static class Song {
        private long id;

        private String id_str;

        private String title;

        private String artists;

        private Chorus chorus;

        private String chorus_v3_infos;
    }
}
