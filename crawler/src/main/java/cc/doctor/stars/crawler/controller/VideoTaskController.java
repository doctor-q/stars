package cc.doctor.stars.crawler.controller;

import cc.doctor.stars.crawler.video.VideoPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("video/task")
@RestController
public class VideoTaskController {

    @Autowired
    private VideoPipeline videoPipeline;

    @GetMapping("run")
    public void run(Boolean failed) {
        videoPipeline.run(Boolean.TRUE.equals(failed));
    }
}
