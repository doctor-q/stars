package cc.doctor.stars.web.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.*;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @PostMapping("login")
    public Response<LoginResponse> login(@RequestBody @Validated LoginRequest request) throws BusinessException {
        return userService.login(request);
    }

    /**
     * 发送验证码
     */
    @PostMapping("register/email/verify")
    public Response<?> emailVerify(@RequestBody @Validated EmailRequest request) {
        return userService.emailVerify(request);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public Response<?> register(@RequestBody @Validated RegisterRequest request) throws BusinessException {
        return userService.register(request);
    }

    /**
     * 注销账号
     */
    @PostMapping("account/close")
    public Response<?> closeAccount() {
        return userService.closeAccount();
    }

    /**
     * 用户列表
     */
    @GetMapping("user/page")
    public PageResponse<UserResponse> userPage() {
        return null;
    }

    /**
     * 详情信息
     */
    @GetMapping("user/detail")
    public Response<UserDetailResponse> userDetail() {
        return null;
    }
}
