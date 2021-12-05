package cn.timelost.hr;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.timelost.hr.config.utils.JWTUtils;
import cn.timelost.hr.pojo.User;
import cn.timelost.hr.service.UserService;

@SpringBootTest
class ApplicationTests {
    
    @Autowired
    UserService userService;
    
    @Test
    void contextLoads() {
    }
    
    @Test
    void register() {
        //注册用户名密码
        String username = "admin";
        String password = "admin123";
        
        String salt = JWTUtils.getSalt();
        Md5Hash md5Hash = new Md5Hash(password, salt, 100);
        User user = User.builder().username(username).password(md5Hash.toHex()).salt(salt).build();
        userService.insert(user);
    }
}
