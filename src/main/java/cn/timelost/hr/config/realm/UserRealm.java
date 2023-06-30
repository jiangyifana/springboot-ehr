package cn.timelost.hr.config.realm;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.timelost.hr.config.JWTToken;
import cn.timelost.hr.config.utils.JWTUtils;
import cn.timelost.hr.dao.RoleDao;
import cn.timelost.hr.pojo.Role;
import cn.timelost.hr.pojo.User;
import cn.timelost.hr.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Jyf
 * @Date: 2021/2/18 20:47
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    
    @Resource
    UserService userService;
    @Resource
    RoleDao roleDao;
    
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        Role role = roleDao.selectByPrimaryKey(user.getRoleId());
        authorizationInfo.addRole(role.getName());
        authorizationInfo.addStringPermission(null);
        return authorizationInfo;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        String username = JWTUtils.getUsername(token);
        
        if (username == null) {
            throw new AuthenticationException("token异常");
        }
        User userBean = userService.findByUsername(username);
        
        if (!JWTUtils.verify(token, username, userBean.getPassword())) {
            throw new AuthenticationException("密码错误");
        }
        return new SimpleAuthenticationInfo(userBean, token, getName());
    }
}
