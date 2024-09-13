package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Resources;
import cc.doctor.stars.biz.model.RsAweme;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
public class RsResponse {
    private Integer id;
    private Integer rsType;
    private Integer rsMimeType;
    private String rsUri;
    private Aweme aweme;

    public RsResponse(Resources resources, RsAweme rsAweme) {
        this.id = resources.getId();
        this.rsType = resources.getRsType();
        this.rsMimeType = resources.getRsMimeType();
        this.rsUri = resources.getRsUri();
        if (rsAweme != null) {
            aweme = new Aweme();
            aweme.setAuthorId(rsAweme.getAuthorId());
            aweme.setAwAuthorNickname(rsAweme.getAwAuNickname());
            aweme.setAwAuthorSecId(rsAweme.getAwAuSecUid());
            aweme.setAwTitle(rsAweme.getAwTitle());
            aweme.setAwCreateTime(LocalDateTime.ofEpochSecond(rsAweme.getAwCreateTime(), 0, ZoneOffset.ofHours(8)));
            aweme.setAwCoverUrl(rsAweme.getAwCoverUrl());
        }
    }

    @Data
    static
    public class Aweme {
        private Integer authorId;
        private String awAuthorNickname;
        private String awAuthorSecId;
        private String awTitle;
        private LocalDateTime awCreateTime;
        private String awCoverUrl;
    }
}
