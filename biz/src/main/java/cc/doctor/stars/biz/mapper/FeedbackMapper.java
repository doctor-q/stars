package cc.doctor.stars.biz.mapper;

import cc.doctor.stars.biz.model.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 反馈表 Mapper 接口
 * </p>
 *
 * @author doctor
 * @since 2024.09.13
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
