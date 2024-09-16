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

    private Map<Integer, RsResponse> getRsResponses(List<Integer> rsIds) {
        if (rsIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<Resources> resources = resourcesMapper.selectBatchIds(rsIds);
        List<RsAweme> rsAwemes = rsAwemeMapper.selectList(new LambdaQueryWrapper<RsAweme>().eq(RsAweme::getRsId, rsIds));
        Map<Integer, RsAweme> awemeMap = rsAwemes.stream().collect(Collectors.toMap(RsAweme::getRsId, v -> v));
        Map<Integer, RsAuthor> authorMap = Collections.emptyMap();
        if (!rsAwemes.isEmpty()) {
            List<RsAuthor> rsAuthors = rsAuthorMapper.selectBatchIds(rsAwemes.stream().map(RsAweme::getAuthorId).collect(Collectors.toList()));
            authorMap = rsAuthors.stream().collect(Collectors.toMap(RsAuthor::getId, v -> v));
        }
        Map<Integer, RsAuthor> finalAuthorMap = authorMap;
        return resources.stream().collect(Collectors.toMap(Resources::getId, rs -> {
            RsAweme aweme = awemeMap.get(rs.getId());
            RsResponse rsResponse = new RsResponse(rs, aweme);
            if (aweme != null) {
                rsResponse.setAuthor(new RsResponse.Author(finalAuthorMap.get(aweme.getAuthorId())));
            }
            return rsResponse;
        }));
    }

    public PageResponse<RsCollectResponse> pageCollectRs(PageRequest<?> request) {
        Page<RsCollect> page = rsCollectMapper.selectPage(request.toPage(),
                new LambdaQueryWrapper<RsCollect>().eq(RsCollect::getUserId, requestContext.getUserId())
                        .eq(RsCollect::getCollectStatus, YesOrNoEnum.YES.getValue()).orderByDesc(RsCollect::getCollectTime));
        List<Integer> rsIds = page.getRecords().stream().map(RsCollect::getRsId).collect(Collectors.toList());
        Map<Integer, RsResponse> rsResponses = getRsResponses(rsIds);
        return PageResponse.pageResponse(page, page.getRecords().stream().map(rsCollect -> new RsCollectResponse(rsCollect, rsResponses.get(rsCollect.getRsId()))).collect(Collectors.toList()));
    }

    public PageResponse<RsHisResponse> pageRsHistory(PageRequest<?> request) {
        Page<RsHistory> page = rsHistoryMapper.selectPage(request.toPage(),
                new LambdaQueryWrapper<RsHistory>().eq(RsHistory::getUserId, requestContext.getUserId())
                        .orderByDesc(RsHistory::getCreateTime));
        List<Integer> rsIds = page.getRecords().stream().map(RsHistory::getRsId).collect(Collectors.toList());
        Map<Integer, RsResponse> rsResponses = getRsResponses(rsIds);
        return PageResponse.pageResponse(page, page.getRecords().stream().map(rsHistory -> new RsHisResponse(rsHistory, rsResponses.get(rsHistory.getId()))).collect(Collectors.toList()));
    }

    public Response<RsDetailResponse> rsDetail(Integer resId) throws BusinessException {
        Resources resources = resourcesMapper.selectById(resId);
        if (resources == null) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "资源错误！");
        }
        RsAweme aweme = rsAwemeMapper.selectOne(new LambdaQueryWrapper<RsAweme>().eq(RsAweme::getRsId, resId));
        RsDetailResponse rsDetailResponse = new RsDetailResponse(resources, aweme);
        if (aweme != null) {
            RsAuthor rsAuthor = rsAuthorMapper.selectById(aweme.getAuthorId());
            rsDetailResponse.setAuthor(new RsResponse.Author(rsAuthor));
        }
        Integer userId = requestContext.getUserId();
        if (userId != null) {
            RsCollect rsCollect = rsCollectMapper.selectOne(new LambdaQueryWrapper<RsCollect>().eq(RsCollect::getUserId, userId).eq(RsCollect::getRsId, resId));
            rsDetailResponse.setCollectStatus(rsCollect == null ? YesOrNoEnum.NO.getValue() : rsCollect.getCollectStatus());
            if (aweme != null) {
                RsAuthorFollow rsAuthorFollow = rsAuthorFollowMapper.selectOne(new LambdaQueryWrapper<RsAuthorFollow>().eq(RsAuthorFollow::getUserId, userId).eq(RsAuthorFollow::getAuthorId, aweme.getAuthorId()));
                rsDetailResponse.setFollowStatus(rsAuthorFollow == null ? YesOrNoEnum.NO.getValue() : rsAuthorFollow.getFollowStatus());
            }
        }
        return Response.success(rsDetailResponse);
    }

    public Response<RsDetailResponse> recommend() throws BusinessException {
        return rsDetail(1);
    }

    public Response<RsDetailResponse> follow() throws BusinessException {
        return rsDetail(2);
    }
}
