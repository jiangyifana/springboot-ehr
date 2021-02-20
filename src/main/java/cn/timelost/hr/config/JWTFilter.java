package cn.timelost.hr.config;

import cn.hutool.json.JSONUtil;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author: Jyf
 * @Date: 2021/2/18 20:54
 */
@Component
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
    /**
     * 认证之前执行该方法
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = SecurityUtils.getSubject();
        return null != subject && subject.isAuthenticated();
    }

    /**
     * 认证未通过执行该方法
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        //完成token登入
        //1.检查请求头中是否含有token
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("Authorization");
        //2. 如果客户端没有携带token，拦下请求
        if (ObjectUtils.isEmpty(token)) {
            token = httpServletRequest.getParameter("token");
            if (ObjectUtils.isEmpty(token)) {
                log.error("未携带Token，禁止访问接口" + ((HttpServletRequest) request).getRequestURI());
                tokenError(response, "未携带Token，禁止访问接口");
                return false;
            }
        }
        //3. 如果有，对进行进行token验证
        JWTToken jwtToken = new JWTToken(token);
        try {
            SecurityUtils.getSubject().login(jwtToken);
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            tokenError(response, e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void tokenError(ServletResponse response, String msg) {
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(String.valueOf(JSONUtil.parse(ResultVo.fail(ResultEnum.AUTHENTICATE_FAIL, msg))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}