package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.model.RsAuthor;
import cc.doctor.stars.biz.model.RsAuthorFollow;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorFollowResponse extends AuthorResponse {
    private Integer userId;
    private Integer followStatus;
    private LocalDateTime followTime;

    public AuthorFollowResponse(RsAuthor author, RsAuthorFollow authorFollow) {
        super(author);
        this.followStatus = YesOrNoEnum.NO.getValue();
        if (authorFollow != null) {
            this.userId = authorFollow.getUserId();
            this.followStatus = authorFollow.getFollowStatus();
            this.followTime = authorFollow.getFollowTime();
        }
    }
}
