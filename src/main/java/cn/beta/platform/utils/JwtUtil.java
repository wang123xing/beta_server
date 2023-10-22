package cn.beta.platform.utils;

import cn.beta.platform.exception.AppException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

import static cn.beta.platform.enums.ResultEnum.USER_NOT_FOUNT;


/**
* JWT工具类
**/
public class JwtUtil {

   // Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
   public static final long EXPIRE_TIME = 15 *12 * 60 * 60 * 1000;

   /**
    * 校验token是否正确
    *
    * @param token  密钥
    * @param secret 用户的密码
    * @return 是否正确
    */
   public static boolean verify(String token, String username, String secret) {
       try {
           // 根据密码生成JWT效验器
           Algorithm algorithm = Algorithm.HMAC256(secret);
           JWTVerifier verifier = JWT.require(algorithm).withClaim("mobile", username).build();
           // 效验TOKEN
           DecodedJWT jwt = verifier.verify(token);
           return true;
       } catch (Exception exception) {
           return false;
       }
   }

   /**
    * 获得token中的信息无需secret解密也能获得
    *
    * @return token中包含的手机号
    */
   public static String getLoginName(String token) {
       try {
           DecodedJWT jwt = JWT.decode(token);
           return jwt.getClaim("login_name").asString();
       } catch (JWTDecodeException e) {
           return null;
       }
   }

   /**
    *
    * @Des 生成签名,5min后过期
    * @param loginName 账号
    * @param secret   用户的密码
    * @return 加密的token
    */
   public static String sign(String loginName, String secret) {
       Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
       Algorithm algorithm = Algorithm.HMAC256(secret);
       // 附带username信息
       return JWT.create().withClaim("login_name", loginName).withExpiresAt(date).sign(algorithm);

   }

   /**
    * 根据request中的token获取用户手机号
    *
    * @param request
    * @return
    * @throws AppException
    */
   public static String getUserNameByToken(HttpServletRequest request) throws AppException {
       String accessToken = request.getHeader("X-Access-Token");
       String username = getLoginName(accessToken);
       if (ConvertUtils.isEmpty(username)) {
           throw new AppException(USER_NOT_FOUNT);
       }
       return username;
   }

   /**
     *  从session中获取变量
    * @param key
    * @return
    */
   public static String getSessionData(String key) {
       String moshi = "";
       if(key.indexOf("}")!=-1){
            moshi = key.substring(key.indexOf("}")+1);
       }
       String returnValue = null;
       if (key.contains("#{")) {
           key = key.substring(2,key.indexOf("}"));
       }
       if (ConvertUtils.isNotEmpty(key)) {
           HttpSession session = SpringContextUtils.getHttpServletRequest().getSession();
           returnValue = (String) session.getAttribute(key);
       }
       if(returnValue!=null){returnValue = returnValue + moshi;}
       return returnValue;
   }
}
