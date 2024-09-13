package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.mapper.RsSearchHistoryMapper;
import cc.doctor.stars.biz.model.RsSearchHistory;
import cc.doctor.stars.web.dto.SearchHisResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private RequestContext requestContext;

    @Autowired
    private RsSearchHistoryMapper searchHistoryMapper;

    public PageResponse<SearchHisResponse> pageSearchHis(PageRequest<?> request) {
        Integer userId = requestContext.getUserId();
        Page<RsSearchHistory> page = searchHistoryMapper.selectPage(request.toPage(), new LambdaQueryWrapper<RsSearchHistory>().eq(RsSearchHistory::getUserId, userId).orderByDesc(RsSearchHistory::getCreateTime));
        return PageResponse.pageResponse(page, page.getRecords().stream().map(SearchHisResponse::new).collect(Collectors.toList()));
    }

    public Response<List<String>> searchGuess() {
        return null;
    }

    public Response<List<String>> searchOthers() {
        return null;
    }
}
