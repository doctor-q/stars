package cc.doctor.stars.web.service;

import cc.doctor.stars.web.dto.SearchHisResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    public PageResponse<SearchHisResponse> pageSearchHis(PageRequest<?> request) {
        return null;
    }

    public Response<List<String>> searchGuess() {
        return null;
    }

    public Response<List<String>> searchOthers() {
        return null;
    }
}
