package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Data
public class RegisterRequest {
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotNull(message = "监护人角色不能为空")
    private Integer role;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "验证码不能为空")
    private String emailVerifyCode;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotNull(message = "孩子性别不能为空")
    private Integer childGender;
    @NotNull(message = "孩子出生年月不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date childBirth;

    public Users toUsers() {
        Users users = new Users();
        users.setNickname(nickname);
        users.setRole(role);
        users.setEmail(email);
        users.setPassword(password);
        users.setChildGender(childGender);
        users.setChildYear(childBirth.getYear());
        users.setChildMonth(childBirth.getMonth() + 1);
        return users;
    }
}
