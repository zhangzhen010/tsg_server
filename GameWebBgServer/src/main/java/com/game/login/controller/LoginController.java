package com.game.login.controller;

import com.game.controller.structs.ResponseBean;
import com.game.user.structs.WebBgUser;
import com.game.utils.ID;
import com.game.utils.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2024/8/13 9:29
 */
@RestController
@Log4j2
@RequestMapping(value = "/tsgbg/login")
public class LoginController {

    private @Resource MongoTemplate mongoTemplate;

    /**
     * 后台登录
     *
     * @param userName
     * @param password
     * @return
     */
    @GetMapping
    public ResponseBean<Object> login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        try {
            //判断输入账号
            if (ObjectUtils.isEmpty(userName) || ObjectUtils.isEmpty(password)) {
                log.error("登录错误，账号或密码为空userName=" + userName + " password=" + password);
                return ResponseBean.fail("Account or password is null!");
            }
            // 查找用户数据
            WebBgUser user = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(userName)), WebBgUser.class);
            if (user == null) {
                log.error("登录错误，用户不存在！userName=" + userName);
                return ResponseBean.fail("Account or password incorrect!");
            }
            // 验证密码
            if (!user.getPassword().equals(encryptPassword(user.getUserName(), password, user.getSalt()))) {
                log.error("登录错误，密码错误！userName=" + userName);
                return ResponseBean.fail("Account or password incorrect!");
            }
            // 生成token
            String jwtToken = JwtUtil.getJwtToken(user.getUserId(), user.getUserName());
            Map<String, Object> resData = new HashMap<>();
            resData.put("token", jwtToken);
            resData.put("account", user.getUserName());
            resData.put("userId", user.getUserId());
            log.info("登录成功！userName=" + userName);
            return ResponseBean.success(resData);
        } catch (Exception e) {
            log.error("后台登录异常：", e);
            return ResponseBean.fail("login error: ".concat(e.getMessage()));
        }
    }

    /**
     * 后台注册账号(后期要屏蔽)
     *
     * @param userName
     * @param password
     * @return
     */
    @GetMapping("/registerUser")
    public ResponseBean<Object> registerUser(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        try {
            //判断输入账号
            if (ObjectUtils.isEmpty(userName) || ObjectUtils.isEmpty(password)) {
                log.error("后台注册账号错误，账号或密码为空userName=" + userName + " password=" + password);
                return ResponseBean.fail("Account or password is null!");
            }
            // 查找用户数据
            WebBgUser user = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(userName)), WebBgUser.class);
            if (user != null) {
                log.error("后台注册账号错误，用户已经存在！userName=" + userName);
                return ResponseBean.fail("Account or password incorrect!");
            }
            user = new WebBgUser();
            user.setUserId(ID.getId());
            user.setUserName(userName);
            user.setSalt(BCrypt.gensalt());
            user.setPassword(encryptPassword(userName, password, user.getSalt()));
            mongoTemplate.insert(user);
            return ResponseBean.success();
        } catch (Exception e) {
            log.error("后台注册账号异常：", e);
            return ResponseBean.fail("registerUser error: ".concat(e.getMessage()));
        }
    }

    /**
     * 加密密码
     *
     * @param userName
     * @param password
     * @param salt
     * @return
     */
    public String encryptPassword(String userName, String password, String salt) {
        return BCrypt.hashpw(userName + password, salt).toLowerCase();
    }

}
