package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.RsAuthorFollowMapper;
import cc.doctor.stars.biz.mapper.RsAuthorMapper;
import cc.doctor.stars.biz.model.RsAuthor;
import cc.doctor.stars.biz.model.RsAuthorFollow;
import cc.doctor.stars.web.dto.AuthorDetailResponse;
import cc.doctor.stars.web.dto.AuthorFollowRequest;
import cc.doctor.stars.web.dto.AuthorFollowResponse;
import cc.doctor.stars.web.dto.AuthorResponse;
import cc.doctor.stars.web.dto.common.PageRequest;
import cc.doctor.stars.web.dto.common.PageResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private RsAuthorMapper authorMapper;

    @Autowired
    private RsAuthorFollowMapper authorFollowMapper;

    @Autowired
    private RequestContext requestContext;

    public PageResponse<AuthorFollowResponse> searchAuthorPage(PageRequest<String> request) {
        Page<RsAuthor> p = authorMapper.selectPage(request.toPage(), new LambdaQueryWrapper<RsAuthor>()
                .like(!StringUtils.isEmpty(request.getData()), RsAuthor::getNickName, request.getData()));
        List<RsAuthor> records = p.getRecords();
        if (records.isEmpty()) {
            return PageResponse.pageResponse(p);
        }
        List<RsAuthorFollow> authorFollows = authorFollowMapper.selectList(new LambdaQueryWrapper<RsAuthorFollow>().eq(RsAuthorFollow::getUserId, requestContext.getUserId())
                .in(RsAuthorFollow::getAuthorId, records.stream().map(RsAuthor::getId).collect(Collectors.toList())));
        Map<Integer, RsAuthorFollow> followMap = authorFollows.stream().collect(Collectors.toMap(RsAuthorFollow::getAuthorId, v -> v));
        return PageResponse.pageResponse(p, records.stream().map(author -> new AuthorFollowResponse(author, followMap.get(author.getId()))).collect(Collectors.toList()));
    }

    public Response<Integer> followAuthor(AuthorFollowRequest request) throws BusinessException {
        RsAuthorFollow authorFollow = authorFollowMapper.selectOne(new LambdaQueryWrapper<RsAuthorFollow>()
                .eq(RsAuthorFollow::getAuthorId, request.getAuthorId()).eq(RsAuthorFollow::getUserId, requestContext.getUserId()));
        // 关注
        if (request.getFollow() == YesOrNoEnum.YES.getValue()) {
            if (authorFollow != null) {
                if (authorFollow.getFollowStatus() == YesOrNoEnum.YES.getValue()) {
                    throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "作者已关注");
                } else {
                    authorFollowMapper.updateById(request.update(authorFollow.getId()));
                }
            } else {
                authorFollowMapper.insert(request.insert(requestContext.getUserId()));
            }
        } else {
            // 取消关注
            if (authorFollow == null || authorFollow.getFollowStatus() == YesOrNoEnum.NO.getValue()) {
                throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "作者未被关注");
            } else {
                authorFollowMapper.updateById(request.update(authorFollow.getId()));
            }
        }
        return Response.success(request.getAuthorId());
    }

    public Response<AuthorDetailResponse> getAuthorDetail(Integer authorId) {
        return null;
    }

    public PageResponse<AuthorResponse> pageFollow(PageRequest<?> request) {
        Page<RsAuthorFollow> followPage = authorFollowMapper.selectPage(request.toPage(), new LambdaQueryWrapper<RsAuthorFollow>()
                .eq(RsAuthorFollow::getUserId, requestContext.getUserId()).eq(RsAuthorFollow::getFollowStatus, YesOrNoEnum.YES.getValue()).orderByDesc(RsAuthorFollow::getFollowTime));
        List<Integer> authorIds = followPage.getRecords().stream().map(RsAuthorFollow::getAuthorId).collect(Collectors.toList());
        if (authorIds.isEmpty()) {
            return PageResponse.pageResponse(followPage);
        }
        List<RsAuthor> rsAuthors = authorMapper.selectBatchIds(authorIds);
        Map<Integer, RsAuthor> authorMap = rsAuthors.stream().collect(Collectors.toMap(RsAuthor::getId, v -> v));
        return PageResponse.pageResponse(followPage, followPage.getRecords().stream().map(f -> new AuthorFollowResponse(authorMap.get(f.getAuthorId()), f)).collect(Collectors.toList()));
    }
}
