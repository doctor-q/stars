package cc.doctor.stars.biz.mapper;

import cc.doctor.stars.biz.model.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author doctor
 * @since 2024.09.12
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

    void insertBatch(List<File> files);
}
