package cc.doctor.stars.web.dto;

import cc.doctor.stars.biz.model.RsSearchHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchHisResponse {
    private String keywords;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public SearchHisResponse(RsSearchHistory searchHistory) {
        this.keywords = searchHistory.getKeywords();
        this.createTime = searchHistory.getCreateTime();
    }
}
