package cc.doctor.stars.web.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDetailResponse {
    private String avatar;
    private String nickname;
    private Integer role;
    private String roleName;
    private Date birth;
    private Integer gender;
    private String ageYearMonth;

    private Integer collectCount;
    private List<RsCollectResponse> rsCollectList;
    private List<RsHisResponse> rsHisList;
    private Integer followCount;
    private List<AuthorResponse> followList;
}
