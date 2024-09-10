package cc.doctor.stars.web.controller;

import cc.doctor.stars.web.dto.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class LogController {
    @GetMapping("*")
    public Response<?> log(HttpServletRequest request) {
        log.info(request.getRequestURI());
        return Response.success();
    }
}
