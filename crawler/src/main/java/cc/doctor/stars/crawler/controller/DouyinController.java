package cc.doctor.stars.crawler.controller;

import cc.doctor.stars.crawler.douyin.aweme.response.AwemeResponse;
import cc.doctor.stars.crawler.service.AwemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("douyin")
public class DouyinController {

    @Autowired
    private AwemeService awemeService;

    @GetMapping("aweme/list")
    public Object getAwemeList(@RequestParam String cookie, @RequestParam String userId) {
        AwemeResponse awemeList = awemeService.loadByUid(userId);
        awemeService.resolveAndStoreAwemes(awemeList);
        return awemeList;
    }
}
