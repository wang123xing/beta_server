package cn.beta.platform.shiro.interceptor;

import cn.beta.platform.annotation.Permission;
import cn.beta.platform.entity.dto.ResultBean;
import cn.beta.platform.utils.ConvertUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static cn.beta.platform.enums.ResultEnum.EXCEPTION;


/**
 * 接口请求鉴权拦截器
 */
@Slf4j
public class MethodInterceptor implements HandlerInterceptor {


    /**
     * 白名单过滤地址
     */
    private static final String[] ONLINE_TEST_PRE = {"/demo"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求的方法是否有注解
        boolean anno = handler.getClass().isAssignableFrom(Permission.class);
        return true;
    }

    /**
     * 地址过滤
     *
     * @param requestPath
     * @return
     */
    private String filterUrl(String requestPath) {
        String url = "";
        if (ConvertUtils.isNotEmpty(requestPath)) {
            url = requestPath.replace("\\", "/");
            url = requestPath.replace("//", "/");
            if (url.indexOf("//") >= 0) {
                url = filterUrl(url);
            }
        }
        return url;
    }

    /**
     * 返回一个错误信息
     *
     * @param response
     * @param authKey
     */
    private void backError(HttpServletResponse response, String authKey) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("auth", "fail");
        try {
            writer = response.getWriter();
            if ("exportXls".equals(authKey)) {
                writer.print("");
            } else {
                ResultBean<?> result = ResultBean.error(EXCEPTION);
                writer.print(JSON.toJSON(result));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
