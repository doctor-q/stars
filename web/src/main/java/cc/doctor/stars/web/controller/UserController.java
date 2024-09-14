package cc.doctor.stars.web.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.*;
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
    public Response<?> register(@RequestBody @Validated UserRegisterRequest request) throws BusinessException {
        return userService.register(request);
    }

    /**
     * 注销账号
     */
    @PostMapping("account/close")
    public Response<?> closeAccount() {
        return userService.closeAccount();
    }

    @GetMapping("user/info")
    public Response<UserInfo> getUser() throws BusinessException {
        return userService.getUser();
    }

    /**
     * 更新用户
     */
    @PostMapping("user/update")
    public Response<?> updateUser(@RequestBody @Validated UserInfo request) {
        return userService.updateUser(request);
    }

    /**
     * 详情信息
     */
    @GetMapping("user/detail")
    public Response<UserDetailResponse> userDetail() throws BusinessException {
        return userService.getUserDetail();
    }

    /**
     * 退出登录
     */
    @GetMapping("logout")
    public Response<?> logout() {
        return userService.logout();
    }
}
