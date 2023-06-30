package cn.timelost.hr.config.aspect;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import cn.timelost.hr.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 操作日志记录处理
 * @author ruoyi
 */
@Aspect
@Component
public class LogAspect {
    
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
    
    @Pointcut("execution(public * cn.timelost.hr.controller.*.*(..))")
    public void log() {
    }
    
    @Around("(log())")
    public Object doBefore(ProceedingJoinPoint point) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        
        // 打印请求相关参数
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        String header = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(header);
        String method = request.getMethod();
        String clientIP = ServletUtil.getClientIP(request);
        
        //get请求过滤
        if (HttpMethod.GET.equals(HttpMethod.resolve(method))) {
            return result;
        }
        
        //获取当前的用户
        User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
        
        Log info = Log.builder()
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .ip(clientIP)
                .url(request.getRequestURL().toString())
                .classMethod(String.format("%s.%s", point.getSignature().getDeclaringTypeName(),
                        point.getSignature().getName()))
                .httpMethod(method)
                .requestParams(getNameAndValue(point))
                .result(result)
                .timeCost(System.currentTimeMillis() - startTime)
                .userAgent(header)
                .browser(ua.getBrowser().toString())
                .os(ua.getOs().toString())
                .username(Optional.ofNullable(loginUser).map(User::getUsername).orElse(null))
                .build();
        
        log.info("Request Log Info : {}", JSONUtil.toJsonStr(info));
        
        return result;
    }
    
    /**
     * 获取方法参数名和参数值
     */
    private Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        final String[] names = methodSignature.getParameterNames();
        final Object[] args = joinPoint.getArgs();
        
        if (ArrayUtil.isEmpty(names) || ArrayUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("{}方法参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = MapUtil.newHashMap();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Log {
        // 线程id
        private String threadId;
        // 线程名称
        private String threadName;
        // ip
        private String ip;
        // url
        private String url;
        // http方法 GET POST PUT DELETE PATCH
        private String httpMethod;
        // 类方法
        private String classMethod;
        // 请求参数
        private Object requestParams;
        // 返回参数
        private Object result;
        // 接口耗时
        private Long timeCost;
        // 操作系统
        private String os;
        // 浏览器
        private String browser;
        // user-agent
        private String userAgent;
        //登录用户
        private String username;
    }
    
}
