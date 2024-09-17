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
 * 搜索历史表
 * </p>
 *
 * @author doctor
 * @since 2024.09.05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("rs_search_history")
public class RsSearchHistory implements Serializable {

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
     * 关键字
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 是否过期
     */
    @TableField("expired")
    private Integer expired;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
