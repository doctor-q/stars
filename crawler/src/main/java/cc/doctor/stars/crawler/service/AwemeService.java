package cc.doctor.stars.crawler.service;

import cc.doctor.stars.biz.enums.RsFileTypeEnum;
import cc.doctor.stars.biz.enums.RsTypeEnum;
import cc.doctor.stars.biz.enums.YesOrNoEnum;
import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.*;
import cc.doctor.stars.biz.model.*;
import cc.doctor.stars.biz.store.StoreFactory;
import cc.doctor.stars.crawler.douyin.AwemeRequests;
import cc.doctor.stars.crawler.douyin.aweme.response.AwemeResponse;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AwemeService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private RsAwemeMapper awemeMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private RsAuthorMapper authorMapper;

    @Autowired
    private VideoTaskMapper videoTaskMapper;

    @Transactional(rollbackFor = Exception.class)
    public void loadByUid(String uid) throws IOException, BusinessException {
        RsAuthor rsAuthor = authorMapper.selectOne(new LambdaQueryWrapper<RsAuthor>().eq(RsAuthor::getUid, uid));
        if (rsAuthor == null) {
            throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "作者不存在，请先上传作者");
        }

        String res = AwemeRequests.getAwemeList(uid);
        File file = new File();
        file.setPub(YesOrNoEnum.YES.getValue());
        file.setFileName(uid);
        file.setStoreType(StoreFactory.defaultStoreType());
        StoreFactory.upload(file, new ByteArrayInputStream(res.getBytes()));
        fileMapper.insert(file);
        AwemeResponse response = JSON.parseObject(res, AwemeResponse.class);
        List<AwemeResponse.Aweme_list> awemeList = response.getAweme_list();
        if (CollectionUtils.isEmpty(awemeList)) {
            return;
        }

        List<RsAweme> rsAwemeList = awemeList.stream().map(AwemeResponse.Aweme_list::toAweme)
                .collect(Collectors.toList());
        List<RsAweme> dupAwemes = awemeMapper.selectList(new LambdaQueryWrapper<RsAweme>().in(RsAweme::getAwemeId, rsAwemeList.stream().map(RsAweme::getAwemeId).collect(Collectors.toList())));
        Set<String> awemeIds = dupAwemes.stream().map(RsAweme::getAwemeId).collect(Collectors.toSet());
        rsAwemeList.removeIf(aweme -> awemeIds.contains(aweme.getAwemeId()));
        List<Resources> resourcesList = rsAwemeList.stream().map(aweme -> {
            Resources resources = new Resources();
            resources.setRsType(RsTypeEnum.DOUYIN.getType());
            resources.setRsFileType(RsFileTypeEnum.VIDEO.getType());
            resources.setAuthorId(rsAuthor.getId());
            resources.setSrcStoreId(file.getId());
            return resources;
        }).collect(Collectors.toList());
        resourcesMapper.insertBatch(resourcesList);
        Iterator<Integer> ids = resourcesList.stream().map(Resources::getId).collect(Collectors.toList()).iterator();
        rsAwemeList.forEach(aweme -> aweme.setRsId(ids.next()));
        awemeMapper.insertBatch(rsAwemeList);
        List<VideoTask> videoTasks = rsAwemeList.stream().map(aweme -> {
            VideoTask videoTask = new VideoTask();
            videoTask.setResourceId(aweme.getRsId());
            videoTask.setOriginUrl(aweme.getAwVPlayUrl());
            videoTask.setCoverUrl(aweme.getAwCoverUrl());
            return videoTask;
        }).collect(Collectors.toList());
        videoTaskMapper.insertBatch(videoTasks);
    }
}
