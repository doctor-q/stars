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
 * 验证码表
 * </p>
 *
 * @author doctor
 * @since 2024.07.04
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("verify_code")
public class VerifyCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 验证码
     */
    @TableField("verify_code")
    private String verifyCode;

    /**
     * 是否失效，0-否，1-是
     */
    @TableField("expired")
    private Byte expired;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
