package cc.doctor.stars.web.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

/**
 * @author zengjinju
 * @date 2019/9/16 上午11:48
 */
@Configuration
@Slf4j
public class JwtConfig {

    public static final String SECRET = "123455";

    @Bean
    public Algorithm algorithm() {
        try {
            return Algorithm.HMAC256(SECRET);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        return null;
    }
}
