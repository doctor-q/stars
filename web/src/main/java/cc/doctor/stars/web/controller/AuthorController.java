package cc.doctor.stars.web.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.AuthorDetailResponse;
import cc.doctor.stars.web.dto.AuthorFollowRequest;
import cc.doctor.stars.web.dto.AuthorFollowResponse;
import cc.doctor.stars.web.dto.AuthorResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("author")
@Validated
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    /**
     * 作者搜索
     */
    @GetMapping("search/page")
    public PageResponse<AuthorFollowResponse> searchAuthorPage(PageRequest<String> request) {
        return authorService.searchAuthorPage(request);
    }

    /**
     * 关注
     */
    @PostMapping("follow")
    public Response<Integer> followAuthor(@RequestBody @Validated AuthorFollowRequest request) throws BusinessException {
        return authorService.followAuthor(request);
    }

    /**
     * 作者详情
     */
    @GetMapping("detail")
    public Response<AuthorDetailResponse> getAuthorDetail(@RequestParam Integer authorId) throws BusinessException {
        return authorService.getAuthorDetail(authorId);
    }
}
