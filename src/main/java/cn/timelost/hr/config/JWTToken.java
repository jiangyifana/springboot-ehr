package cn.timelost.hr.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: Jyf
 * @Date: 2021/2/18 20:49
 */
public class JWTToken implements AuthenticationToken {

    private final String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}