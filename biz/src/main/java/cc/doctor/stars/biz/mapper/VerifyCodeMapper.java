package cc.doctor.stars.biz.mapper;

import cc.doctor.stars.biz.model.VerifyCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 验证码表 Mapper 接口
 * </p>
 *
 * @author doctor
 * @since 2024.07.04
 */
@Mapper
public interface VerifyCodeMapper extends BaseMapper<VerifyCode> {

    void expiredOld(String email);
}
