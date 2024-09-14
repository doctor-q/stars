package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.FileMapper;
import cc.doctor.stars.biz.mapper.UsersMapper;
import cc.doctor.stars.biz.mapper.VerifyCodeMapper;
import cc.doctor.stars.biz.model.File;
import cc.doctor.stars.biz.model.Users;
import cc.doctor.stars.biz.model.VerifyCode;
import cc.doctor.stars.biz.store.StoreFactory;
import cc.doctor.stars.web.dto.*;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class UserService {

    private static final long VERIFY_CODE_EXPIRED_SECONDS = 5 * 60;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private VerifyCodeMapper verifyCodeMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RequestContext requestContext;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private RsService rsService;

    @Autowired
    private AuthorService authorService;

    public Response<LoginResponse> login(LoginRequest request) throws BusinessException {
        Users users = usersMapper.selectOne(new QueryWrapper<Users>().eq("email", request.getEmail()).eq("password", request.getPassword()));
        if (users == null) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "账号或密码错误");
        }
        String token = jwtService.generateAccountToken(users);
        return Response.success(new LoginResponse(users.getNickname(), token));
    }

    public Response<?> register(UserRegisterRequest request) throws BusinessException {
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
        if (verifyCode.getExpired() == YesOrNoEnum.YES.getValue() || (verifyCode.getCreateTime().toEpochSecond(ZoneOffset.ofHours(8)) + VERIFY_CODE_EXPIRED_SECONDS < (System.currentTimeMillis() / 1000))) {
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
        // 发送邮件
        return Response.success();
    }

    public Response<?> logout() {
        return null;
    }

    public Response<?> closeAccount() {
        Integer userId = requestContext.getUserId();
        Users users = new Users();
        users.setId(userId);
        users.setClosed(YesOrNoEnum.YES.getValue());
        usersMapper.updateById(users);
        return Response.success();
    }

    public Response<?> updateUser(UserInfo request) {
        Users users = request.toUsers();
        Integer userId = requestContext.getUserId();
        users.setId(userId);
        usersMapper.updateById(users);
        return Response.success();
    }

    public Response<UserInfo> getUser() throws BusinessException {
        return Response.success(getUserInfo());
    }

    private UserInfo getUserInfo() throws BusinessException {
        Integer userId = requestContext.getUserId();
        Users users = usersMapper.selectById(userId);
        if (users == null) {
            throw new BusinessException(BusinessException.INVALID_TOKEN, "账号错误");
        }
        UserInfo userInfo = new UserInfo(users);
        if (users.getAvatar() != null) {
            File file = fileMapper.selectById(users.getAvatar());
            String url = StoreFactory.createUrl(file);
            userInfo.setAvatarUrl(url);
        }
        return userInfo;
    }

    public Response<UserDetailResponse> getUserDetail() throws BusinessException {
        UserInfo userInfo = getUserInfo();
        UserDetailResponse userDetail = new UserDetailResponse(userInfo);
        // 收藏
        PageRequest<?> pageRequest = PageRequest.pageRequest(1, 20);
        PageResponse<RsCollectResponse> pageCollectRs = rsService.pageCollectRs(pageRequest);
        userDetail.setRsCollectPage(pageCollectRs);
        // 历史
        PageResponse<RsHisResponse> pageRsHistory = rsService.pageRsHistory(pageRequest);
        userDetail.setRsHisPage(pageRsHistory);
        // 关注
        PageResponse<AuthorResponse> pageFollow = authorService.pageFollow(pageRequest);
        userDetail.setFollowPage(pageFollow);
        return Response.success(userDetail);
    }
}
