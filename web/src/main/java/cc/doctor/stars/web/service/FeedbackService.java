package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.mapper.FeedbackMapper;
import cc.doctor.stars.biz.mapper.FileMapper;
import cc.doctor.stars.biz.model.Feedback;
import cc.doctor.stars.biz.model.File;
import cc.doctor.stars.biz.store.StoreFactory;
import cc.doctor.stars.web.dto.FeedbackResponse;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.filter.RequestContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FeedbackService {

    @Autowired
    private RequestContext requestContext;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileService fileService;

    public Response<?> submit(String feedback, MultipartFile[] fileList) throws BusinessException {
        Integer userId = requestContext.getUserId();
        Feedback fb = new Feedback();
        fb.setUserId(userId);
        fb.setFeedback(feedback);
        if (fileList != null) {
            try {
                List<Integer> fileIds = fileService.storeMultiparts(fileList);
                fb.setFileIds(fileIds.stream().map(Object::toString).collect(Collectors.joining(",")));

            } catch (IOException e) {
                log.error("存储文件异常", e);
                throw new BusinessException(BusinessException.INVALID_INPUT_CODE, "存储文件异常");
            }
        }
        feedbackMapper.insert(fb);
        return Response.success();
    }

    public Response<List<FeedbackResponse>> list() {
        List<Feedback> feedbacks = feedbackMapper.selectList(new LambdaQueryWrapper<Feedback>().eq(Feedback::getUserId, requestContext.getUserId()).orderByDesc(Feedback::getCreatedAt));
        List<FeedbackResponse> responseList = feedbacks.stream().map(fb -> {
            FeedbackResponse feedbackResponse = new FeedbackResponse();
            feedbackResponse.setFeedback(fb.getFeedback());
            if (!StringUtils.isEmpty(fb.getFileIds())) {
                List<File> files = fileMapper.selectBatchIds(Arrays.stream(fb.getFileIds().split(",")).map(Integer::parseInt).collect(Collectors.toList()));
                feedbackResponse.setUrls(files.stream().map(StoreFactory::createUrl).collect(Collectors.toList()));
            }
            return feedbackResponse;
        }).collect(Collectors.toList());
        return Response.success(responseList);
    }
}
