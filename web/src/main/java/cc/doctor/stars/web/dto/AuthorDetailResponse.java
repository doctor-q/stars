package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.RsAuthor;
import cc.doctor.stars.biz.model.RsAuthorFollow;
import cc.doctor.stars.web.dto.common.PageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AuthorDetailResponse extends AuthorFollowResponse {
    private PageResponse<RsResponse> rsPage;
    private PageResponse<UserInfo> userPage;

    public AuthorDetailResponse(RsAuthor rsAuthor, RsAuthorFollow authorFollow) {
        super(rsAuthor, authorFollow);
    }
}
