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
 * 视频处理
 * </p>
 *
 * @author doctor
 * @since 2024.09.20
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("video_task")
public class VideoTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源id
     */
    @TableField("resource_id")
    private Integer resourceId;

    /**
     * 下载地址
     */
    @TableField("origin_url")
    private String originUrl;

    /**
     * 是否已上传
     */
    @TableField("upload")
    private Integer upload;

    /**
     * 上传文件id
     */
    @TableField("upload_id")
    private Integer uploadId;

    /**
     * 是否已分帧
     */
    @TableField("framed")
    private Integer framed;

    /**
     * 分帧路径
     */
    @TableField("frame_id")
    private Integer frameId;

    /**
     * 是否已提取字幕
     */
    @TableField("extract_sub_title")
    private Integer extractSubTitle;

    /**
     * 字幕
     */
    @TableField("extract_sub_title_text")
    private String extractSubTitleText;

    /**
     * 封面字幕
     */
    @TableField("extract_cover_text")
    private String extractCoverText;

    /**
     * 任务状态
     */
    @TableField("task_status")
    private Integer taskStatus;

    /**
     * 错误栈
     */
    @TableField("error_stack")
    private String errorStack;

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
