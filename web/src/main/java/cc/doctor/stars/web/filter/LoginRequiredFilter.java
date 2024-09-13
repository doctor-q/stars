package cc.doctor.stars.web.filter;

import cc.doctor.stars.biz.exception.BusinessException;
import cc.doctor.stars.biz.model.Users;
import cc.doctor.stars.web.dto.common.Response;
import cc.doctor.stars.web.service.JwtService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
public class LoginRequiredFilter extends GenericFilterBean {
    private static final String HEADER_TOKEN = "token";

    @Value("${filter.exclude.prefix}")
    private List<String> excludes;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RequestContext requestContext;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String requestURI = httpServletRequest.getRequestURI();
            if (!CollectionUtils.isEmpty(excludes) && excludes.stream().anyMatch(requestURI::startsWith)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            String token = httpServletRequest.getHeader(HEADER_TOKEN);
            if (token == null || invalidToken(token)) {
                servletResponse.getOutputStream().write(JSON.toJSONBytes(Response.fail(BusinessException.INVALID_TOKEN, "请重新登录")));
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean invalidToken(String token) {
        Users users = jwtService.getAccountByToken(token);
        if (users == null) {
            return true;
        }
        requestContext.setValue(RequestContext.REQUEST_USER_ID, users.getId());
        return false;
    }
}
