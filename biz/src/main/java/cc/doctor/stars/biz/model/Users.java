package cc.doctor.stars.biz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author doctor
 * @since 2024.07.03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 头像
     */
    private Integer avatar;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 密码MD5
     */
    @TableField("password")
    private String password;

    /**
     * 孩子性别
     */
    @TableField("child_gender")
    private Integer childGender;

    /**
     * 孩子出生日期
     */
    @TableField("child_birth")
    private LocalDate childBirth;

    /**
     * 账户是否关闭
     */
    private Integer closed;

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
