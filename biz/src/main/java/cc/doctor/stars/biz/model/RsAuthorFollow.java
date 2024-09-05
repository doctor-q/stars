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
 * 资源收藏表
 * </p>
 *
 * @author doctor
 * @since 2024.09.05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("rs_author_follow")
public class RsAuthorFollow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 资源id
     */
    @TableField("author_id")
    private Integer authorId;

    /**
     * 关注状态，0-关注，1-取消关注
     */
    @TableField("follow_status")
    private Integer followStatus;

    /**
     * 关注时间
     */
    @TableField("follow_time")
    private LocalDateTime followTime;

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
