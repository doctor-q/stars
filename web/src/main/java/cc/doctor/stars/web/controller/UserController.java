package cc.doctor.stars.web.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.*;
import cc.doctor.stars.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Response<LoginResponse> login(@RequestBody LoginRequest request) throws BusinessException {
        return userService.login(request);
    }

    @PostMapping("register/email/verify")
    public Response<?> emailVerify(@RequestBody EmailRequest request) {
        return userService.emailVerify(request);
    }

    @PostMapping("register")
    public Response<?> register(@RequestBody RegisterRequest request) throws BusinessException {
        return userService.register(request);
    }
}
