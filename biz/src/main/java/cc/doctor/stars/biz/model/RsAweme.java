package cc.doctor.stars.biz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 抖音短视频
 * </p>
 *
 * @author doctor
 * @since 2024.06.28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("rs_aweme")
public class RsAweme implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源id
     */
    @TableField("rs_id")
    private Integer rsId;

    /**
     * 作者id
     */
    @TableField("author_id")
    private Integer authorId;

    /**
     * 短视频id
     */
    @TableField("aweme_id")
    private String awemeId;

    /**
     * 标题
     */
    @TableField("aw_title")
    private String awTitle;

    /**
     * 抖音创建时间
     */
    @TableField("aw_create_time")
    private Long awCreateTime;

    /**
     * 欣赏数
     */
    @TableField("aw_st_admire_count")
    private Integer awStAdmireCount;

    /**
     * 评论数
     */
    @TableField("aw_st_comment_count")
    private Integer awStCommentCount;

    /**
     * 点赞数
     */
    @TableField("aw_st_digg_count")
    private Integer awStDiggCount;

    /**
     * 收藏数
     */
    @TableField("aw_st_collect_count")
    private Integer awStCollectCount;

    /**
     * 播放数
     */
    @TableField("aw_st_play_count")
    private Integer awStPlayCount;

    /**
     * 分享数
     */
    @TableField("aw_st_share_count")
    private Integer awStShareCount;

    /**
     * 播放地址
     */
    @TableField("aw_v_play_url")
    private String awVPlayUrl;

    /**
     * 播放id
     */
    @TableField("aw_v_play_uri")
    private String awVPlayUri;

    /**
     * 视频大小
     */
    @TableField("aw_v_play_size")
    private Integer awVPlaySize;

    /**
     * 封面地址
     */
    @TableField("aw_cover_url")
    private String awCoverUrl;

    /**
     * 是否置顶
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
