package cc.doctor.stars.web.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JwtConfig {

    public static final String SECRET = "123455";

    @Bean
    public Algorithm algorithm() {
        return Algorithm.HMAC256(SECRET);
    }
}
