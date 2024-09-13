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
 * 文件表
 * </p>
 *
 * @author doctor
 * @since 2024.09.12
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 是否公有
     */
    private Integer pub;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 存储类型
     */
    @TableField("store_type")
    private Integer storeType;

    /**
     * 文件存储路径
     */
    @TableField("path")
    private String path;

    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
