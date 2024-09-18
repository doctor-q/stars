package cc.doctor.stars.web.controller;

import cc.doctor.stars.web.dto.RsResponse;
import cc.doctor.stars.web.dto.SearchHisResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索历史
     */
    @GetMapping("his/page")
    public PageResponse<SearchHisResponse> pageSearchHis(PageRequest<?> request) {
        return searchService.pageSearchHis(request);
    }

    /**
     * 猜你想搜
     */
    @GetMapping("guess")
    public Response<List<String>> searchGuess() {
        return searchService.searchGuess();
    }

    /**
     * 大家在搜
     */
    @GetMapping("others")
    public Response<List<String>> searchOthers() {
        return searchService.searchOthers();
    }

    /**
     * 资源搜索
     */
    @GetMapping("rs")
    public Response<List<RsResponse>> searchResource(String keywords, Integer size) {
        return searchService.searchResource(keywords, size);
    }

    /**
     * suggest
     */
    @GetMapping("suggest")
    public Response<List<String>> searchSuggest(String keywords) {
        return searchService.searchSuggest(keywords);
    }
}
