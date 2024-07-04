package cc.doctor.stars.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String nickname;
    private String token;

    public LoginResponse(String nickname, String token) {
        this.nickname = nickname;
        this.token = token;
    }
}
