package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.model.RsAuthorFollow;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthorFollowRequest {
    private Integer authorId;
    private Integer follow;

    public RsAuthorFollow update(Integer id) {
        RsAuthorFollow authorFollow = new RsAuthorFollow();
        if (follow == YesOrNoEnum.YES.getValue()) {
            authorFollow.setFollowStatus(YesOrNoEnum.YES.getValue());
            authorFollow.setFollowTime(LocalDateTime.now());
        } else {
            authorFollow.setFollowStatus(YesOrNoEnum.NO.getValue());
        }
        return authorFollow;
    }

    public RsAuthorFollow insert() {
        RsAuthorFollow authorFollow = new RsAuthorFollow();
        authorFollow.setFollowStatus(YesOrNoEnum.YES.getValue());
        authorFollow.setFollowTime(LocalDateTime.now());
        return authorFollow;
    }
}
