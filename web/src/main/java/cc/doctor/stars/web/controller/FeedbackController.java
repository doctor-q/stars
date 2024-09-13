package cc.doctor.stars.web.controller;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.web.dto.FeedbackResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("submit")
    public Response<?> submit(@RequestParam String feedback, MultipartFile[] fileList) throws BusinessException {
        return feedbackService.submit(feedback, fileList);
    }

    @GetMapping("list")
    public Response<List<FeedbackResponse>> list() {
        return feedbackService.list();
    }
}
