package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.Resources;
import cc.doctor.stars.biz.model.RsAuthor;
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
    private Author author;

    public RsResponse(Resources resources) {
        this(resources, null);
    }

    public RsResponse(Resources resources, RsAweme rsAweme) {
        this.id = resources.getId();
        this.rsType = resources.getRsType();
        this.rsMimeType = resources.getRsMimeType();
        this.rsUri = resources.getRsUri();
        if (rsAweme != null) {
            aweme = new Aweme();
            aweme.setAuthorId(rsAweme.getAuthorId());
            aweme.setAwTitle(rsAweme.getAwTitle());
            aweme.setAwCreateTime(LocalDateTime.ofEpochSecond(rsAweme.getAwCreateTime(), 0, ZoneOffset.ofHours(8)));
            aweme.setAwCoverUrl(rsAweme.getAwCoverUrl());
        }
    }

    public RsResponse(RsResponse rsResponse) {
        this.id = rsResponse.id;
        this.rsType = rsResponse.rsType;
        this.rsMimeType = rsResponse.rsMimeType;
        this.rsUri = rsResponse.rsUri;
        this.aweme = rsResponse.aweme;
        this.author = rsResponse.author;
    }

    @Data
    public static class Aweme {
        private Integer authorId;
        private String awTitle;
        private LocalDateTime awCreateTime;
        private String awCoverUrl;
    }

    @Data
    @NoArgsConstructor
    public static class Author {
        private Integer id;
        private String nickname;
        private String avatarUrl;

        public Author(RsAuthor author) {
            this.id = author.getId();
            this.nickname = author.getNickName();
            this.avatarUrl = author.getAvatarUrl();
        }
    }
}
