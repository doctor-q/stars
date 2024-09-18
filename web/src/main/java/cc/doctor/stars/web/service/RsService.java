package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.*;
import cc.doctor.stars.biz.model.*;
import cc.doctor.stars.web.dto.*;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
    private RsAuthorMapper rsAuthorMapper;

    @Autowired
    private RsAuthorFollowMapper rsAuthorFollowMapper;

    @Autowired
    private RequestContext requestContext;

    public Response<Integer> collectRs(RsCollectRequest request) throws BusinessException {
        RsCollect rsCollect = rsCollectMapper.selectOne(new LambdaQueryWrapper<RsCollect>()
                .eq(RsCollect::getRsId, request.getResId()).eq(RsCollect::getUserId, requestContext.getUserId()));
        // 收藏
        if (request.getCollect() == YesOrNoEnum.YES.getValue()) {
            if (rsCollect != null) {
                if (rsCollect.getCollectStatus() == YesOrNoEnum.YES.getValue()) {
                    throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "资源已收藏");
                } else {
                    rsCollectMapper.updateById(request.update(rsCollect.getId()));
                }
            } else {
                rsCollectMapper.insert(request.insert(requestContext.getUserId()));
            }
        } else {
            // 取消收藏
            if (rsCollect == null || rsCollect.getCollectStatus() == YesOrNoEnum.NO.getValue()) {
                throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "资源未被收藏");
            } else {
                rsCollectMapper.updateById(request.update(rsCollect.getId()));
            }
        }
        return Response.success(request.getResId());
    }

    public Map<Integer, RsResponse> getRsResponseByIds(List<Integer> rsIds) {
        if (rsIds.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Resources> resources = resourcesMapper.selectBatchIds(rsIds);
        return getRsResponses(resources);
    }

    private Map<Integer, RsResponse> getRsResponses(List<Resources> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return Collections.emptyMap();
        }
        List<Integer> rsIds = resources.stream().map(Resources::getId).collect(Collectors.toList());
        List<RsAweme> rsAwemes = rsAwemeMapper.selectList(new LambdaQueryWrapper<RsAweme>().in(RsAweme::getRsId, rsIds));
        Map<Integer, RsAweme> awemeMap = rsAwemes.stream().collect(Collectors.toMap(RsAweme::getRsId, v -> v));
        List<RsAuthor> rsAuthors = rsAuthorMapper.selectBatchIds(resources.stream().map(Resources::getAuthorId).collect(Collectors.toList()));
        Map<Integer, RsAuthor> authorMap = rsAuthors.stream().collect(Collectors.toMap(RsAuthor::getId, v -> v));
        return resources.stream().collect(Collectors.toMap(Resources::getId, rs -> new RsResponse(rs, awemeMap.get(rs.getId()), authorMap.get(rs.getAuthorId()))));
    }

    public PageResponse<RsCollectResponse> pageCollectRs(PageRequest<?> request) {
        Page<RsCollect> page = rsCollectMapper.selectPage(request.toPage(),
                new LambdaQueryWrapper<RsCollect>().eq(RsCollect::getUserId, requestContext.getUserId())
                        .eq(RsCollect::getCollectStatus, YesOrNoEnum.YES.getValue()).orderByDesc(RsCollect::getCollectTime));
        List<Integer> rsIds = page.getRecords().stream().map(RsCollect::getRsId).collect(Collectors.toList());
        Map<Integer, RsResponse> rsResponses = getRsResponseByIds(rsIds);
        return PageResponse.pageResponse(page, page.getRecords().stream().map(rsCollect -> new RsCollectResponse(rsCollect, rsResponses.get(rsCollect.getRsId()))).collect(Collectors.toList()));
    }

    public PageResponse<RsHisResponse> pageRsHistory(PageRequest<?> request) {
        Page<RsHistory> page = rsHistoryMapper.selectPage(request.toPage(),
                new LambdaQueryWrapper<RsHistory>().eq(RsHistory::getUserId, requestContext.getUserId())
                        .orderByDesc(RsHistory::getCreateTime));
        List<Integer> rsIds = page.getRecords().stream().map(RsHistory::getRsId).collect(Collectors.toList());
        Map<Integer, RsResponse> rsResponses = getRsResponseByIds(rsIds);
        return PageResponse.pageResponse(page, page.getRecords().stream().map(rsHistory -> new RsHisResponse(rsHistory, rsResponses.get(rsHistory.getId()))).collect(Collectors.toList()));
    }

    public Response<RsDetailResponse> rsDetail(Integer resId) throws BusinessException {
        return Response.success(getRsDetail(resId));
    }

    private RsDetailResponse getRsDetail(Integer resId) throws BusinessException {
        Resources resources = resourcesMapper.selectById(resId);
        if (resources == null) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "资源错误！");
        }
        RsAweme aweme = rsAwemeMapper.selectOne(new LambdaQueryWrapper<RsAweme>().eq(RsAweme::getRsId, resId));
        RsAuthor rsAuthor = rsAuthorMapper.selectById(resources.getAuthorId());
        RsDetailResponse rsDetailResponse = new RsDetailResponse(resources, aweme, rsAuthor);
        Integer userId = requestContext.getUserId();
        if (userId != null) {
            RsCollect rsCollect = rsCollectMapper.selectOne(new LambdaQueryWrapper<RsCollect>().eq(RsCollect::getUserId, userId).eq(RsCollect::getRsId, resId));
            rsDetailResponse.setCollectStatus(rsCollect == null ? YesOrNoEnum.NO.getValue() : rsCollect.getCollectStatus());
            if (aweme != null) {
                RsAuthorFollow rsAuthorFollow = rsAuthorFollowMapper.selectOne(new LambdaQueryWrapper<RsAuthorFollow>().eq(RsAuthorFollow::getUserId, userId)
                        .eq(RsAuthorFollow::getAuthorId, resources.getAuthorId()));
                rsDetailResponse.setFollowStatus(rsAuthorFollow == null ? YesOrNoEnum.NO.getValue() : rsAuthorFollow.getFollowStatus());
            }
        }
        return rsDetailResponse;
    }

    public Response<List<RsDetailResponse>> recommend() throws BusinessException {
        return Response.success(Collections.singletonList(getRsDetail(1)));
    }

    public Response<List<RsDetailResponse>> follow() throws BusinessException {
        return Response.success(Collections.singletonList(getRsDetail(2)));
    }

    public PageResponse<RsResponse> pageAuthorRs(PageRequest<Integer> pageRequest) {
        Page<Resources> page = resourcesMapper.selectPage(pageRequest.toPage(), new LambdaQueryWrapper<Resources>().eq(Resources::getAuthorId, pageRequest.getData()));
        Map<Integer, RsResponse> rsResponses = getRsResponses(page.getRecords());
        return PageResponse.pageResponse(page, page.getRecords().stream().map(resources -> rsResponses.get(resources.getId())).collect(Collectors.toList()));
    }
}
