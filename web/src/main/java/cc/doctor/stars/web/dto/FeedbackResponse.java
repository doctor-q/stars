package cc.doctor.stars.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedbackResponse {
    private String feedback;
    private List<String> urls;
}
