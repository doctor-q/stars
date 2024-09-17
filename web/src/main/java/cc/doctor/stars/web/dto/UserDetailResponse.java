package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.enums.RoleEnum;
import cc.doctor.stars.biz.model.Users;
import cc.doctor.stars.web.dto.common.PageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserDetailResponse extends UserInfo {
    private String roleName;
    private String childAge;

    private PageResponse<RsCollectResponse> rsCollectPage;
    private PageResponse<RsHisResponse> rsHisPage;
    private PageResponse<AuthorFollowResponse> followPage;

    public UserDetailResponse(Users users) {
        super(users);
        this.roleName = RoleEnum.getName(users.getRole());
        this.childAge = ageYearMonth(users.getChildBirth());
    }

    public UserDetailResponse(UserInfo userInfo) {
        super(userInfo);
        this.roleName = RoleEnum.getName(userInfo.getRole());
        this.childAge = ageYearMonth(userInfo.getChildBirth());
    }

    private String ageYearMonth(LocalDate localDate) {
        LocalDate now = LocalDate.now();
        int months = now.getYear() * 12 + now.getMonthValue() - (localDate.getYear() * 12 + localDate.getMonthValue());
        return String.format("%d岁%d月", months / 12, months % 12);
    }
}
