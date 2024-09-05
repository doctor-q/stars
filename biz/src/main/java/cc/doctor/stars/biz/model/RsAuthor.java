package cc.doctor.stars.biz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源作者
 * </p>
 *
 * @author doctor
 * @since 2024.09.05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("rs_author")
public class RsAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 平台用户id
     */
    @TableField("uid")
    private String uid;

    /**
     * 资源来源类型
     */
    @TableField("rs_type")
    private Byte rsType;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 头像地址
     */
    @TableField("avatar_url")
    private String avatarUrl;
}
