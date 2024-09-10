package cc.doctor.stars.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailRequest {
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
