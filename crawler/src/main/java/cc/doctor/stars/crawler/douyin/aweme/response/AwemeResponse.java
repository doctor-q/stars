package cc.doctor.stars.crawler.douyin.aweme.response;

import cc.doctor.stars.biz.model.RsAweme;
import cc.doctor.stars.biz.utils.CollectUtils;
import lombok.Data;

import java.util.List;

@Data
public class AwemeResponse {

    @Data
    public static class Statistics {
        private int admire_count;
        //评论数
        private int comment_count;
        //点赞数
        private int digg_count;
        //收藏数
        private int collect_count;
        //播放数
        private int play_count;
        //转发数
        private int share_count;
    }

    @Data
    public static class Review_result {
        private int review_status;
    }

    @Data
    public static class Status {
        private int listen_video_status;

        private boolean is_delete;

        private boolean allow_share;

        private boolean is_prohibited;

        private boolean in_reviewing;

        private int part_see;

        private int private_status;

        private Review_result review_result;
    }

    @Data
    public static class Text_extra {
        private int start;

        private int end;

        private int type;

        private String hashtag_name;

        private String hashtag_id;

        private boolean is_commerce;

        private int caption_start;

        private int caption_end;
    }

    @Data
    public static class Share_info {
        private String share_url;

        private String share_link_desc;
    }

    @Data
    public static class Limit_free {
        private boolean in_free;
    }

    @Data
    public static class Market_info {
        private Limit_free limit_free;

        private String marketing_tag;
    }

    @Data
    public static class Entertainment_product_info {
        private String sub_title;

        private Market_info market_info;
    }

    @Data
    public static class Risk_infos {
        private boolean vote;

        private boolean warn;

        private boolean risk_sink;

        private int type;

        private String content;
    }


    @Data
    public static class Xigua_base_info {
        private int status;

        private long star_altar_order_id;

        private int star_altar_type;

        private long item_id;

    }


    @Data
    public static class Distribute_circle {
        private int distribute_type;

        private boolean campus_block_interaction;

        private boolean is_campus;
    }


    @Data
    public static class Item_warn_notification {
        private int type;

        private boolean show;

        private String content;
    }


    @Data
    public static class Download_info {
        private int level;
    }


    @Data
    public static class Fail_info {
        private int code;

        private String reason;
    }


    @Data
    public static class Duet_info {
        private int level;

        private Fail_info fail_info;

    }

    @Data
    public static class Video_control {
        private boolean allow_download;

        private int share_type;

        private int show_progress_bar;

        private int draft_progress_bar;

        private boolean allow_duet;

        private boolean allow_react;

        private int prevent_download_type;

        private boolean allow_dynamic_wallpaper;

        private int timer_status;

        private boolean allow_music;

        private boolean allow_stitch;

        private boolean allow_douplus;

        private boolean allow_share;

        private boolean share_grayed;

        private boolean download_ignore_visibility;

        private boolean duet_ignore_visibility;

        private boolean share_ignore_visibility;

        private Download_info download_info;

        private Duet_info duet_info;

        private boolean allow_record;

        private String disable_record_reason;
    }


    @Data
    public static class Aweme_control {
        private boolean can_forward;

        private boolean can_share;

        private boolean can_comment;

        private boolean can_show_comment;

    }


    @Data
    public static class Visual_search_info {
        private boolean is_show_img_entrance;

        private boolean is_ecom_img;
    }

    @Data
    public static class Impression_data {
        private List<String> group_id_list_a;

        private List<String> group_id_list_b;

        private List<Long> similar_id_list_a;

        private List<Long> similar_id_list_b;

        private List<String> group_id_list_c;
    }

    @Data
    public static class Words {
        private String word;

        private String word_id;

        private String info;
    }

    @Data
    public static class Suggest_word {
        private List<Words> words;

        private String scene;

        private String icon_url;

        private String hint_text;

    }

    @Data
    public static class Suggest_words {
        private List<Suggest_word> suggest_words;
    }

    @Data
    public static class Comment_permission_info {
        private int comment_permission_status;

        private boolean can_comment;

        private boolean item_detail_entry;

        private boolean press_entry;

        private boolean toast_guide;
    }


    @Data
    public static class Series_paid_info {
        private int series_paid_status;

        private int item_price;
    }


    @Data
    public static class Image_album_music_info {
        private int begin_time;

        private int end_time;

        private int volume;
    }


    @Data
    public static class Video_tag {
        private long tag_id;

        private String tag_name;

        private int level;
    }


    @Data
    public static class Photo_search_entrance {
        private int ecom_type;
    }

    @Data
    public static class Aweme_list {
        private String aweme_id;

        private String desc;

        private long create_time;

        private Author author;

        private Music music;

        private int friend_interaction;

        private Video video;

        private String share_url;

        private int user_digged;

        private Statistics statistics;

        private Status status;

        private int collection_corner_mark;

        private List<Text_extra> text_extra;

        private int is_top;

        private String ref_voice_modify_id_list;

        private Share_info share_info;

        private int original;

        private String video_labels;

        private String yumme_recreason;

        private boolean is_ads;

        private int duration;

        private int aweme_type;

        private Entertainment_product_info entertainment_product_info;

        private int video_share_edit_status;

        private String image_infos;

        private Risk_infos risk_infos;

        private String slides_music_beats;

        private int user_recommend_status;

        private String position;

        private String uniqid_position;

        private String comment_list;

        private long author_user_id;

        private String trends_infos;

        private List<String> geofencing;

        private int boost_status;

        private int media_type;

        private String region;

        private String video_text;

        private int disable_relation_bar;

        private int collect_stat;

        private String label_top_text;

        private List<String> promotions;

        private String group_id;

        private boolean prevent_download;

        private String nickname_position;

        private String challenge_position;

        private int image_crop_ctrl;

        private Xigua_base_info xigua_base_info;

        private String origin_text_extra;

        private String common_bar_info;

        private String long_video;

        private Distribute_circle distribute_circle;

        private String dislike_dimension_list_v2;

        private Item_warn_notification item_warn_notification;

        private boolean mark_largely_following;

        private String interaction_stickers;

        private String voice_modify_id_list;

        private String origin_comment_ids;

        private String commerce_config_data;

        private boolean is_use_music;

        private Video_control video_control;

        private Aweme_control aweme_control;

        private String item_title;

        private boolean enable_comment_sticker_rec;

        private String tts_id_list;

        private String jump_tab_info_list;

        private String anchors;

        private String hybrid_label;

        private String geofencing_regions;

        private String caption;

        private int is_story;

        private String authentication_token;

        private int author_mask_tag;

        private int is_24_story;

        private String cover_labels;

        private String create_scale_type;

        private String ref_tts_id_list;

        private int guide_btn_type;

        private int activity_video_type;

        private Visual_search_info visual_search_info;

        private String images;

        private String relation_labels;

        private String reply_smart_emojis;

        private Impression_data impression_data;

        private String packed_clips;

        private String libfinsert_task_id;

        private String social_tag_list;

        private Suggest_words suggest_words;

        private boolean duet_aggregate_in_music_tab;

        private boolean is_duet_sing;

        private Comment_permission_info comment_permission_info;

        private String original_images;

        private Series_paid_info series_paid_info;

        private String img_bitrate;

        private long comment_gid;

        private Image_album_music_info image_album_music_info;

        private List<Video_tag> video_tag;

        private int is_collects_selected;

        private String chapter_list;

        private boolean is_image_beat;

        private String dislike_dimension_list;

        private String standard_bar_info_list;

        private Photo_search_entrance photo_search_entrance;

        private boolean is_life_item;

        private String main_arch_common;

        private String image_list;

        private String component_info_v2;

        public static  RsAweme toAweme(Aweme_list aweme_list) {
            RsAweme aweme = new RsAweme();
            aweme.setAwemeId(aweme_list.aweme_id);
            aweme.setAwTitle(aweme_list.desc);
            aweme.setAwCreateTime(aweme_list.create_time);
            aweme.setAwStAdmireCount(aweme_list.statistics.admire_count);
            aweme.setAwStCommentCount(aweme_list.statistics.comment_count);
            aweme.setAwStDiggCount(aweme_list.statistics.digg_count);
            aweme.setAwStCollectCount(aweme_list.statistics.collect_count);
            aweme.setAwStPlayCount(aweme_list.statistics.play_count);
            aweme.setAwStShareCount(aweme_list.statistics.share_count);
            aweme.setAwVPlayUrl(CollectUtils.join(aweme_list.video.getPlay_addr().getUrl_list()));
            aweme.setAwVPlayUri(aweme_list.video.getPlay_addr().getUri());
            aweme.setAwVPlaySize(aweme_list.video.getPlay_addr().getData_size());
            aweme.setAwCoverUrl(CollectUtils.join(aweme_list.video.getCover().getUrl_list()));
            aweme.setAwAuUid(aweme_list.author.getUid());
            aweme.setAwAuNickname(aweme_list.author.getNickname());
            aweme.setAwAuSecUid(aweme_list.author.getSec_uid());
            aweme.setIsTop((byte)aweme_list.is_top);
            return aweme;

        }
    }

    @Data
    public static class Log_pb {
        private String impr_id;
    }

    // 状态码，0-正常
    private int status_code;

    // 最小游标
    private long min_cursor;

    // 最大游标
    private long max_cursor;

    // 是否有更多
    private int has_more;

    // 视频列表
    private List<Aweme_list> aweme_list;

    // 时间列表
    private List<String> time_list;

    private Log_pb log_pb;

    // 请求游标
    private long request_item_cursor;

    private int post_serial;

    private int replace_series_cover;


}
