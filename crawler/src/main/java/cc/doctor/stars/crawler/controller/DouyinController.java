package cc.doctor.stars.crawler.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.crawler.service.AwemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("douyin")
public class DouyinController {

    @Autowired
    private AwemeService awemeService;

    @GetMapping("aweme/list")
    public Object getAwemeList(@RequestParam String cookie, @RequestParam String userId) throws IOException, BusinessException {
        awemeService.loadByUid(userId);
        return "success";
    }
}
