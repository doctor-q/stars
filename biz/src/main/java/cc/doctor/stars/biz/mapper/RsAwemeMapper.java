package cc.doctor.stars.biz.mapper;

import cc.doctor.stars.biz.model.RsAweme;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 抖音短视频 Mapper 接口
 * </p>
 *
 * @author doctor
 * @since 2024.06.28
 */
@Mapper
public interface RsAwemeMapper extends BaseMapper<RsAweme> {

    void insertBatch(List<RsAweme> awemeList);
}
