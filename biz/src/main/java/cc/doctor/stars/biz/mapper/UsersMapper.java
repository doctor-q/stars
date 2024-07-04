package cc.doctor.stars.biz.mapper;

import cc.doctor.stars.biz.model.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author doctor
 * @since 2024.07.03
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}
