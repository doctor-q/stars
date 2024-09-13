package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.ResourcesMapper;
import cc.doctor.stars.biz.mapper.RsAwemeMapper;
import cc.doctor.stars.biz.mapper.RsCollectMapper;
import cc.doctor.stars.biz.mapper.RsHistoryMapper;
import cc.doctor.stars.biz.model.Resources;
import cc.doctor.stars.biz.model.RsAweme;
import cc.doctor.stars.biz.model.RsCollect;
import cc.doctor.stars.biz.model.RsHistory;
import cc.doctor.stars.web.dto.RsCollectRequest;
import cc.doctor.stars.web.dto.RsCollectResponse;
import cc.doctor.stars.web.dto.RsDetailResponse;
import cc.doctor.stars.web.dto.RsHisResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RsService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private RsCollectMapper rsCollectMapper;

    @Autowired
    private RsAwemeMapper rsAwemeMapper;

    @Autowired
    private RsHistoryMapper rsHistoryMapper;

    @Autowired
    private RequestContext requestContext;

    public Response<?> collectRs(RsCollectRequest request) throws BusinessException {
        RsCollect rsCollect = rsCollectMapper.selectOne(new LambdaQueryWrapper<RsCollect>()
                .eq(RsCollect::getRsId, request.getResId()).eq(RsCollect::getUserId, null));
        // 关注
        if (request.getCollect() == YesOrNoEnum.YES.getValue()) {
            if (rsCollect != null) {
                if (rsCollect.getCollectStatus() == YesOrNoEnum.YES.getValue()) {
                    throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "资源已关注");
                } else {
                    rsCollectMapper.updateById(request.update(rsCollect.getId()));
                }
            } else {
                rsCollectMapper.insert(request.insert(requestContext.getUserId()));
            }
        } else {
            // 取消关注
            if (rsCollect == null || rsCollect.getCollectStatus() == YesOrNoEnum.NO.getValue()) {
                throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "作者未被关注");
            } else {
                rsCollectMapper.updateById(request.update(rsCollect.getId()));
            }
        }
        return Response.success();
    }

    public PageResponse<RsCollectResponse> pageCollectRs(PageRequest<?> request) {
        Page<RsCollect> page = rsCollectMapper.selectPage(request.toPage(),
                new LambdaQueryWrapper<RsCollect>().eq(RsCollect::getUserId, requestContext.getUserId())
                        .eq(RsCollect::getCollectStatus, YesOrNoEnum.YES.getValue()).orderByDesc(RsCollect::getCollectTime));
        List<Integer> rsIds = page.getRecords().stream().map(RsCollect::getRsId).collect(Collectors.toList());
        List<Resources> resources = resourcesMapper.selectBatchIds(rsIds);
        Map<Integer, Resources> resourcesMap = resources.stream().collect(Collectors.toMap(Resources::getId, v -> v));
        List<RsAweme> rsAwemes = rsAwemeMapper.selectList(new LambdaQueryWrapper<RsAweme>().eq(RsAweme::getRsId, rsIds));
        Map<Integer, RsAweme> awemeMap = rsAwemes.stream().collect(Collectors.toMap(RsAweme::getRsId, v -> v));
        return PageResponse.pageResponse(page, page.getRecords().stream().map(rsCollect ->
                new RsCollectResponse(rsCollect, resourcesMap.get(rsCollect.getRsId()), awemeMap.get(rsCollect.getRsId()))).collect(Collectors.toList()));
    }

    public PageResponse<RsHisResponse> pageRsHistory(PageRequest<?> request) {
        Page<RsHistory> page = rsHistoryMapper.selectPage(request.toPage(),
                new LambdaQueryWrapper<RsHistory>().eq(RsHistory::getUserId, requestContext.getUserId())
                        .orderByDesc(RsHistory::getCreateTime));
        List<Integer> rsIds = page.getRecords().stream().map(RsHistory::getRsId).collect(Collectors.toList());
        List<Resources> resources = resourcesMapper.selectBatchIds(rsIds);
        Map<Integer, Resources> resourcesMap = resources.stream().collect(Collectors.toMap(Resources::getId, v -> v));
        List<RsAweme> rsAwemes = rsAwemeMapper.selectList(new LambdaQueryWrapper<RsAweme>().eq(RsAweme::getRsId, rsIds));
        Map<Integer, RsAweme> awemeMap = rsAwemes.stream().collect(Collectors.toMap(RsAweme::getRsId, v -> v));
        return PageResponse.pageResponse(page, page.getRecords().stream().map(rsHistory ->
                new RsHisResponse(rsHistory, resourcesMap.get(rsHistory.getRsId()), awemeMap.get(rsHistory.getRsId()))).collect(Collectors.toList()));
    }

    public Response<RsDetailResponse> rsDetail(Integer resId) {
        return null;
    }
}
