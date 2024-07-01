package cc.doctor.stars.crawler.service;

import cc.doctor.stars.biz.mapper.RsAwemeMapper;
import cc.doctor.stars.biz.model.RsAweme;
import cc.doctor.stars.crawler.douyin.AwemeRequests;
import cc.doctor.stars.crawler.douyin.aweme.response.AwemeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwemeService {
    @Autowired
    private RsAwemeMapper awemeMapper;

    public AwemeResponse loadByUid(String uid) {
        return AwemeRequests.getAwemeList(uid);
    }

    public void resolveAndStoreAwemes(AwemeResponse response) {
        List<AwemeResponse.Aweme_list> awemeList = response.getAweme_list();
        List<RsAweme> rsAwemeList = awemeList.stream().map(AwemeResponse.Aweme_list::toAweme).collect(Collectors.toList());
        awemeMapper.insertBatch(rsAwemeList);
    }
}
