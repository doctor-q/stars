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
 * 资源表
 * </p>
 *
 * @author doctor
 * @since 2024.09.05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("resources")
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 资源来源类型
     */
    @TableField("rs_type")
    private Byte rsType;

    /**
     * 资源文件类型
     */
    @TableField("rs_mime_type")
    private Byte rsMimeType;

    /**
     * 资源路径
     */
    @TableField("rs_uri")
    private String rsUri;

    /**
     * 资源存储路径
     */
    @TableField("rs_store_uri")
    private String rsStoreUri;

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
