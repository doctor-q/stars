package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.RsAuthor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorResponse {
    private Integer id;
    private String uid;
    private Byte rsType;
    private String nickName;
    private String description;
    private String avatarUrl;

    public AuthorResponse(RsAuthor author) {
        this.id = author.getId();
        this.uid = author.getUid();
        this.rsType = author.getRsType();
        this.nickName = author.getNickName();
        this.description = author.getDescription();
        this.avatarUrl = author.getAvatarUrl();
    }
}
