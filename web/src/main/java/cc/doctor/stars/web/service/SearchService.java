package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.mapper.RsAwemeMapper;
import cc.doctor.stars.biz.mapper.RsSearchHistoryMapper;
import cc.doctor.stars.biz.model.RsAweme;
import cc.doctor.stars.biz.model.RsSearchHistory;
import cc.doctor.stars.web.dto.RsResponse;
import cc.doctor.stars.web.dto.SearchHisResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchService {

    @Autowired
    private RequestContext requestContext;

    @Autowired
    private RsSearchHistoryMapper searchHistoryMapper;

    @Autowired
    private RsAwemeMapper awemeMapper;

    @Autowired
    private RsService rsService;

    public PageResponse<SearchHisResponse> pageSearchHis(PageRequest<?> request) {
        Integer userId = requestContext.getUserId();
        Page<RsSearchHistory> page = searchHistoryMapper.selectPage(request.toPage(), new LambdaQueryWrapper<RsSearchHistory>()
                .eq(RsSearchHistory::getUserId, userId).eq(RsSearchHistory::getExpired, YesOrNoEnum.NO.getValue()).orderByDesc(RsSearchHistory::getCreateTime));
        return PageResponse.pageResponse(page, page.getRecords().stream().map(SearchHisResponse::new).collect(Collectors.toList()));
    }

    public Response<List<String>> searchGuess() {
        return null;
    }

    public Response<List<String>> searchOthers() {
        return null;
    }

    public Response<List<RsResponse>> searchResource(String keywords, Integer size) {
        try {
            addHistory(keywords);
        } catch (Exception e) {
            log.error("添加查询历史失败", e);
        }
        List<RsAweme> rsAwemes = awemeMapper.selectList(new Page<>(1, size), new LambdaQueryWrapper<RsAweme>()
                .like(!StringUtils.isEmpty(keywords), RsAweme::getAwTitle, keywords));
        if (rsAwemes.isEmpty()) {
            return Response.success(Collections.emptyList());
        }
        List<Integer> rsIds = rsAwemes.stream().map(RsAweme::getRsId).collect(Collectors.toList());
        Map<Integer, RsResponse> resourceMap = rsService.getRsResponseByIds(rsIds);
        return Response.success(rsAwemes.stream().map(aweme -> resourceMap.get(aweme.getRsId())).collect(Collectors.toList()));
    }

    private void addHistory(String keywords) {
        if (!StringUtils.isEmpty(keywords) && requestContext.getUserId() != null) {
            RsSearchHistory up = new RsSearchHistory();
            up.setExpired(YesOrNoEnum.YES.getValue());
            searchHistoryMapper.update(up, new LambdaQueryWrapper<RsSearchHistory>().eq(RsSearchHistory::getKeywords, keywords));
            RsSearchHistory searchHis = new RsSearchHistory();
            searchHis.setUserId(requestContext.getUserId());
            searchHis.setKeywords(keywords);
            searchHistoryMapper.insert(searchHis);
        }
    }

    public Response<List<String>> searchSuggest(String keywords) {
        List<String> list = new ArrayList<>();
        list.add(keywords + "a");
        list.add(keywords + "b");
        return Response.success(list);
    }
}
