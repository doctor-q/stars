package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.mapper.ResourcesMapper;
import cc.doctor.stars.biz.mapper.RsAuthorMapper;
import cc.doctor.stars.biz.mapper.RsAwemeMapper;
import cc.doctor.stars.biz.mapper.RsSearchHistoryMapper;
import cc.doctor.stars.biz.model.Resources;
import cc.doctor.stars.biz.model.RsAuthor;
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
    private ResourcesMapper resourcesMapper;

    @Autowired
    private RsAuthorMapper rsAuthorMapper;

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
        List<RsAuthor> rsAuthors = rsAuthorMapper.selectBatchIds(rsAwemes.stream().map(RsAweme::getAuthorId).collect(Collectors.toList()));
        Map<Integer, RsAuthor> authorMap = rsAuthors.stream().collect(Collectors.toMap(RsAuthor::getId, v -> v));
        List<Integer> rsIds = rsAwemes.stream().map(RsAweme::getRsId).collect(Collectors.toList());
        List<Resources> resources = resourcesMapper.selectBatchIds(rsIds);
        Map<Integer, Resources> resourcesMap = resources.stream().collect(Collectors.toMap(Resources::getId, v -> v));
        return Response.success(rsAwemes.stream().map(aweme -> new RsResponse(resourcesMap.get(aweme.getRsId()), aweme, authorMap.get(aweme.getAuthorId()))).collect(Collectors.toList()));
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
}
