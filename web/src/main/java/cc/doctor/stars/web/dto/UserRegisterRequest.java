package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Users;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterRequest extends UserInfo {
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "验证码不能为空")
    private String emailVerifyCode;
    @NotBlank(message = "密码不能为空")
    private String password;

    public Users toUsers() {
        Users users = super.toUsers();
        users.setEmail(email);
        users.setPassword(password);
        return users;
    }
}
