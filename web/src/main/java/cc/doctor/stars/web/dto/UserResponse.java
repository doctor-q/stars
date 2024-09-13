package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.enums.RoleEnum;
import cc.doctor.stars.biz.model.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private Integer avatar;
    private String nickname;
    private Integer role;
    private String roleName;

    public UserResponse(Users users) {
        this.avatar = users.getAvatar();
        this.nickname = users.getNickname();
        this.role = users.getRole();
        this.roleName = RoleEnum.getName(users.getRole());
    }
}
