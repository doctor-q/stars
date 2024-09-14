package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.RsAuthor;
import cc.doctor.stars.biz.model.RsAuthorFollow;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorFollowResponse extends AuthorResponse {
    private Integer userId;
    private LocalDateTime followTime;

    public AuthorFollowResponse(RsAuthor author, RsAuthorFollow authorFollow) {
        super(author);
        this.userId = authorFollow.getUserId();
        this.followTime = authorFollow.getFollowTime();
    }
}
