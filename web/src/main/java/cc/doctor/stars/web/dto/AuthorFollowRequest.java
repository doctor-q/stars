package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.model.RsAuthorFollow;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AuthorFollowRequest {
    @NotNull(message = "作者不能为空")
    private Integer authorId;
    @NotNull(message = "关注状态不能为空")
    private Integer follow;

    public RsAuthorFollow update(Integer id) {
        RsAuthorFollow authorFollow = new RsAuthorFollow();
        authorFollow.setId(id);
        if (follow == YesOrNoEnum.YES.getValue()) {
            authorFollow.setFollowStatus(YesOrNoEnum.YES.getValue());
            authorFollow.setFollowTime(LocalDateTime.now());
        } else {
            authorFollow.setFollowStatus(YesOrNoEnum.NO.getValue());
        }
        return authorFollow;
    }

    public RsAuthorFollow insert(Integer userId) {
        RsAuthorFollow authorFollow = new RsAuthorFollow();
        authorFollow.setAuthorId(authorId);
        authorFollow.setUserId(userId);
        authorFollow.setFollowStatus(YesOrNoEnum.YES.getValue());
        authorFollow.setFollowTime(LocalDateTime.now());
        return authorFollow;
    }
}
