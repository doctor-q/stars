package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Users;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "验证码不能为空")
    private String emailVerifyCode;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotNull(message = "孩子性别不能为空")
    private Byte childGender;
    @NotNull(message = "孩子出生年月不能为空")
    private Integer childYear;
    @NotNull(message = "孩子出生年月不能为空")
    private Integer childMonth;

    public Users toUsers() {
        Users users = new Users();
        users.setNickname(nickname);
        users.setEmail(email);
        users.setPassword(password);
        users.setChildGender(childGender);
        users.setChildYear(childYear);
        users.setChildMonth(childMonth);
        return users;
    }
}
