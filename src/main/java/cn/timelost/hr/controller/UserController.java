package cn.timelost.hr.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.timelost.hr.config.JWTToken;
import cn.timelost.hr.config.utils.JWTUtils;
import cn.timelost.hr.enums.ResultEnum;
import cn.timelost.hr.pojo.Role;
import cn.timelost.hr.pojo.User;
import cn.timelost.hr.service.UserService;
import cn.timelost.hr.vo.ResultVo;
import cn.timelost.hr.vo.UserVo;
import cn.timelost.hr.vo.input.UserForm;

/**
 * @author: Jyf
 * @Date: 2021/2/18 21:19
 */
@RestController
public class UserController {
    
    @Autowired
    UserService userService;
    
    /**
     * 验证码过期时间 60s
     */
    private static TimedCache<String, String> cache = CacheUtil.newTimedCache(60 * 1000);
    
    @PostMapping("/user/login")
    public ResultVo login(@RequestBody @Valid UserForm user) {
        String timestamp = user.getTimestamp();
        if (ObjectUtils.isEmpty(timestamp) || ObjectUtils.isEmpty(user.getCode())) {
            return ResultVo.fail(ResultEnum.CODE_NOT_EXIST);
        }
        if (ObjectUtils.isEmpty(cache.get(timestamp))) {
            return ResultVo.fail(ResultEnum.CODE_FAIL);
        }
        String code = cache.get(timestamp);
        if (!code.equals(user.getCode())) {
            return ResultVo.fail(ResultEnum.CODE_FAIL);
        }
        String username = user.getUsername();
        String password = user.getPassword();
        User us = userService.findByUsername(username);
        String salt = us.getSalt();
        Md5Hash md5Hash = new Md5Hash(password, salt, 100);
        //生成token
        String token = JWTUtils.sign(username, md5Hash.toHex());
        //执行登入：（出现异常被全局异常捕捉）
        SecurityUtils.getSubject().login(new JWTToken(token));
        return ResultVo.success(token);
    }
    
    @PostMapping("/user/register")
    @RequiresRoles("admin")
    public ResultVo add(@RequestBody @Valid UserForm us) {
        String salt = JWTUtils.getSalt();
        String password = new Md5Hash(us.getPassword(), salt, 100).toHex();
        User user = new User();
        BeanUtils.copyProperties(us, user);
        user.setSalt(salt);
        user.setPassword(password);
        userService.insert(user);
        String token = JWTUtils.sign(user.getUsername(), password);
        return ResultVo.success(token);
    }
    
    @GetMapping("/user/get")
    public ResultVo get(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        String username = JWTUtils.getUsername(token);
        return ResultVo.success(username);
    }
    
    @GetMapping("/user/list")
    @RequiresRoles("admin")
    public PageInfo<UserVo> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return userService.findList(page, size);
    }
    
    @DeleteMapping("/user/{id}")
    @RequiresRoles("admin")
    public void delete(@PathVariable Integer id) {
        userService.deleteById(id);
    }
    
    @DeleteMapping("/user")
    @RequiresRoles("admin")
    public ResultVo deleteIdIn(@RequestBody Set<Integer> id) {
        userService.deleteByIdIn(id);
        return ResultVo.success();
    }
    
    @PutMapping("/user/{id}")
    @RequiresRoles("admin")
    public void update(@PathVariable Integer id, @RequestBody UserForm us) {
        User user = new User();
        BeanUtils.copyProperties(us, user);
        if (ObjectUtils.isEmpty(us.getPassword())) {
            user.setPassword(null);
        } else {
            String salt = JWTUtils.getSalt();
            String password = new Md5Hash(us.getPassword(), salt, 100).toHex();
            user.setPassword(password);
            user.setSalt(salt);
        }
        user.setId(id);
        userService.updateById(user);
    }
    
    @GetMapping("/image")
    public void code(@RequestParam(value = "timestamp") String timestamp, HttpServletResponse response)
            throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 20);
        lineCaptcha.write(response.getOutputStream());
        cache.put(timestamp, lineCaptcha.getCode());
    }
    
    @GetMapping("/role/select")
    public List<Role> role() {
        return userService.roleList();
    }
}
