package cc.doctor.stars.web.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.RsCollectRequest;
import cc.doctor.stars.web.dto.RsCollectResponse;
import cc.doctor.stars.web.dto.RsDetailResponse;
import cc.doctor.stars.web.dto.RsHisResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rs")
@Validated
public class RsController {

    @Autowired
    private RsService rsService;


    /**
     * 收藏资源
     *
     * @return 资源id
     */
    @PostMapping("collect")
    public Response<Integer> collectRs(@RequestBody @Validated RsCollectRequest request) throws BusinessException {
        return rsService.collectRs(request);
    }

    /**
     * 收藏列表
     */
    @GetMapping("collect/page")
    public PageResponse<RsCollectResponse> pageCollectRs(PageRequest<?> request) {
        return rsService.pageCollectRs(request);
    }

    /**
     * 资源历史
     */
    @GetMapping("history/page")
    public PageResponse<RsHisResponse> pageRsHistory(PageRequest<?> request) {
        return rsService.pageRsHistory(request);
    }

    /**
     * 资源详情
     */
    @GetMapping("detail")
    public Response<RsDetailResponse> rsDetail(@RequestParam Integer resId) throws BusinessException {
        return rsService.rsDetail(resId);
    }

    /**
     * 推荐
     */
    @GetMapping("recommend")
    public Response<RsDetailResponse> recommend() throws BusinessException {
        return rsService.recommend();
    }

    /**
     * 关注
     */
    @GetMapping("follow")
    public Response<RsDetailResponse> follow() throws BusinessException {
        return rsService.follow();
    }
}
