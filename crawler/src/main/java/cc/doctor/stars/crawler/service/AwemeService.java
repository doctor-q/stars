package cc.doctor.stars.crawler.service;

import cc.doctor.stars.biz.mapper.ResourcesMapper;
import cc.doctor.stars.biz.mapper.RsAwemeMapper;
import cc.doctor.stars.biz.model.RsAweme;
import cc.doctor.stars.crawler.douyin.AwemeRequests;
import cc.doctor.stars.crawler.douyin.aweme.response.AwemeResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AwemeService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Autowired
    private RsAwemeMapper awemeMapper;

    public AwemeResponse loadByUid(String uid) {
        return AwemeRequests.getAwemeList(uid);
    }

    public void resolveAndStoreAwemes(AwemeResponse response) {
        List<AwemeResponse.Aweme_list> awemeList = response.getAweme_list();
        List<RsAweme> rsAwemeList = awemeList.stream().map(AwemeResponse.Aweme_list::toAweme).collect(Collectors.toList());
        if (rsAwemeList.isEmpty()) {
            return;
        }
        List<RsAweme> dupAwemes = awemeMapper.selectList(new LambdaQueryWrapper<RsAweme>().in(RsAweme::getAwemeId, rsAwemeList.stream().map(RsAweme::getAwemeId).collect(Collectors.toList())));
        Set<String> awemeIds = dupAwemes.stream().map(RsAweme::getAwemeId).collect(Collectors.toSet());
        rsAwemeList.removeIf(aweme -> awemeIds.contains(aweme.getAwemeId()));
        awemeMapper.insertBatch(rsAwemeList);
    }
}
