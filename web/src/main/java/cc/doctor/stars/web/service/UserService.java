package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.UsersMapper;
import cc.doctor.stars.biz.mapper.VerifyCodeMapper;
import cc.doctor.stars.biz.model.Users;
import cc.doctor.stars.biz.model.VerifyCode;
import cc.doctor.stars.web.dto.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private static final long VERIFY_CODE_EXPIRED_MILLS = 5 * 60 * 1000;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    @Autowired
    private JwtService jwtService;

    public Response<LoginResponse> login(LoginRequest request) throws BusinessException {
        Users users = usersMapper.selectOne(new QueryWrapper<Users>().eq("email", request.getEmail()).eq("password", request.getPassword()));
        if (users == null) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "账号或密码错误");
        }
        String token = jwtService.generateAccountToken(users);
        return Response.success(new LoginResponse(users.getNickname(), token));
    }

    public Response<?> register(RegisterRequest request) throws BusinessException {
        Long count = usersMapper.selectCount(new QueryWrapper<Users>().eq("email", request.getEmail()));
        if (count > 0) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "账户已存在");
        }
        List<VerifyCode> verifyCodes = verifyCodeMapper.selectList(new QueryWrapper<VerifyCode>().eq("email", request.getEmail())
                .eq("verify_code", request.getEmailVerifyCode()).orderByDesc("create_time").last("limit 1"));
        if (verifyCodes.isEmpty()) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "验证码错误");
        }
        VerifyCode verifyCode = verifyCodes.get(0);
        if (verifyCode.getExpired() == YesOrNoEnum.YES.getValue() || (verifyCode.getCreateTime().toEpochSecond(ZoneOffset.ofHours(8)) + VERIFY_CODE_EXPIRED_MILLS < System.currentTimeMillis())) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "验证码已过期");
        }
        Users users = request.toUsers();
        usersMapper.insert(users);
        return Response.success();
    }

    private static String generateVerifyCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    public Response<?> emailVerify(EmailRequest request) {
        verifyCodeMapper.expiredOld(request.getEmail());
        String code = generateVerifyCode();
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setEmail(request.getEmail());
        verifyCode.setVerifyCode(code);
        verifyCodeMapper.insert(verifyCode);
        return Response.success();
    }

    public Response<?> logout() {
        return null;
    }
}
