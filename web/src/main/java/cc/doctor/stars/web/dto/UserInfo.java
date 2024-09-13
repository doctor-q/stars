package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserInfo {
    private Integer avatar;
    private String avatarUrl;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotNull(message = "监护人角色不能为空")
    private Integer role;
    @NotNull(message = "孩子性别不能为空")
    private Integer childGender;
    @NotNull(message = "孩子出生年月不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate childBirth;

    public UserInfo(Users users) {
        this.avatar = users.getAvatar();
        this.nickname = users.getNickname();
        this.role = users.getRole();
        this.childGender = users.getChildGender();
        this.childBirth = users.getChildBirth();
    }

    public Users toUsers() {
        Users users = new Users();
        users.setAvatar(avatar);
        users.setNickname(nickname);
        users.setRole(role);
        users.setChildGender(childGender);
        users.setChildBirth(childBirth);
        return users;
    }
}
