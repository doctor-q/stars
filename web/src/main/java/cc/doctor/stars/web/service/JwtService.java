package cc.doctor.stars.web.service;

import cc.doctor.stars.biz.model.Users;
import cc.doctor.stars.biz.utils.DateUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author zengjinju
 * @date 2019/9/16 上午11:49
 */
@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private static final String HEADER_CLAIM_ACCOUNT_ID = "_user_id";
    private static final String HEADER_CLAIM_ACCOUNT_NAME = "_user_name";
    private static final String HEADER_CLAIM_ACCOUNT_PHONE = "_user_phone";

    @Autowired
    private Algorithm algorithm;

    public String generateAccountToken(Users account) {
        return JWT.create().withClaim(HEADER_CLAIM_ACCOUNT_ID, account.getId())
                .withClaim(HEADER_CLAIM_ACCOUNT_NAME, account.getNickname())
                .withClaim(HEADER_CLAIM_ACCOUNT_PHONE, account.getEmail())
                .withExpiresAt(DateUtils.addHour(new Date(), 24))        //过期时间
                .sign(algorithm);
    }

    public Users getAccountByToken(String accessToken) {
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            Map<String, Claim> claims = decodedJWT.getClaims();
            Users user = new Users();
            Claim userId = claims.get(HEADER_CLAIM_ACCOUNT_ID);
            if (userId == null) {
                return null;
            }
            user.setId(userId.asInt());
            Claim accountName = claims.get(HEADER_CLAIM_ACCOUNT_NAME);
            user.setNickname(accountName.asString());
            user.setEmail(claims.get(HEADER_CLAIM_ACCOUNT_PHONE).asString());
            return user;
        } catch (Exception e) {
            log.error("token解析异常", e);
        }
        return null;
    }

}
