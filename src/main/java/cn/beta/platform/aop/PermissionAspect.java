package cn.beta.platform.aop;

import cn.beta.platform.annotation.Permission;
import cn.beta.platform.annotation.RateLimit;
import cn.beta.platform.utils.NotifyUtil;
import cn.beta.platform.utils.HostUtils;
import cn.beta.platform.utils.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Aspect
@Component
public class PermissionAspect {

    @Autowired
    NotifyUtil notifyUtil;
    @Autowired
    RedisUtil redisUtil;

    @Around("@annotation(permission)" )
    public Object invokeMethod(ProceedingJoinPoint joinPoint, Permission permission) throws NoSuchMethodException {
        final Set<String> permissionCodeSet = new HashSet<>(32);
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Class clazz = joinPoint.getTarget().getClass();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer= new LocalVariableTableParameterNameDiscoverer();
        String [] paraNameArr=localVariableTableParameterNameDiscoverer.getParameterNames(clazz.getMethod(joinPoint.getSignature().getName()));
        try {
            if (!clazz.isAnnotationPresent(Permission.class)) {
                rateLimit(request,clazz,paraNameArr,joinPoint.getArgs());
                return joinPoint.proceed();
            }
            String classPermissionCode = "";
            Annotation annotation = clazz.getAnnotation(Permission.class);
            if (annotation instanceof Permission) {
                Permission permAnnotation = (Permission) annotation;
                classPermissionCode = permAnnotation.Param();
            }
            if (!StringUtils.isEmpty(classPermissionCode)) {
                String[] arrMethodAccess = classPermissionCode.split(",");
                permissionCodeSet.addAll(Arrays.asList(arrMethodAccess));
            }
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            String methodPermissionCode = "";
            if (method.isAnnotationPresent(Permission.class)) {
                Permission permAnnotation = method.getAnnotation(Permission.class);
                methodPermissionCode = permAnnotation.Param();
            }
            if (!StringUtils.isEmpty(methodPermissionCode)) {
                String[] arrMethodAccess = methodPermissionCode.split(",");
                permissionCodeSet.addAll(Arrays.asList(arrMethodAccess));
            }
            if (permissionCodeSet.isEmpty()) {
                rateLimit(request,clazz,paraNameArr,joinPoint.getArgs());
                return joinPoint.proceed();
            }
            rateLimit(request,clazz,paraNameArr,joinPoint.getArgs());
            joinPoint.proceed();//调用目标方法
        } catch (Exception e) {
            notifyUtil.sendDingDingMessage(e);
        } catch (Throwable throwable) {
            notifyUtil.sendDingDingMessage(throwable);
        }
        return null;
    }

    /**
     * 执行限流流速为 rateLimit.value()
     *
     * @param clazz
     */
    public void rateLimit(HttpServletRequest request, Class clazz, String[] paraNameArr, Object[] args) {
        if (clazz.isAnnotationPresent(RateLimit.class)) {
            Annotation annotation = clazz.getAnnotation(RateLimit.class);
            if (annotation instanceof RateLimit) {
                RateLimit rateLimit = (RateLimit) annotation;
                redisUtil.accquire(HostUtils.getIpAddress(request),rateLimit.url(),parseKey(rateLimit.key(),paraNameArr,args),rateLimit.limit());
            }
        }
    }
    private String parseKey(String express, String [] paraNameArr,Object[] args) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        for(int i=0;i<paraNameArr.length;i++){
            context.setVariable(paraNameArr[i], args[i]);
        }
        return new SpelExpressionParser().parseExpression(express).getValue(context,String.class);
    }
}
