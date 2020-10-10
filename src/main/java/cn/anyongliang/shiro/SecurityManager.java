package cn.anyongliang.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * create by Rock-Ayl 2019-5-24
 * shiro的核心之一
 * 所有权限的管理者
 */
public class SecurityManager {

    //初始化管理者(用它管控所有的权限判定)
    public static final SecurityManager securityManager = SecurityManager.VOID();

    //new一个对象 ^_^
    public static SecurityManager VOID() {
        return new SecurityManager();
    }

    private SecurityManager() {
        //初始化
        init();
    }

    //初始化
    private void init() {
        //初始化管理者
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //将数据库实现类绑定到管理者
        defaultSecurityManager.setRealm(DatabaseRealm.VOID());
        //将 SecurityManager 实例绑定给 SecurityUtils
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }

    //获取当前线程主题
    private Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    //获取登录用的token
    private UsernamePasswordToken getToken(String userName, String password) {
        return new UsernamePasswordToken(userName, password);
    }

    //传入用户id，返回用户的Subject
    private Subject getSubject(Long userId) {
        //获取主题
        Subject subject = getSubject();
        //认证登录
        try {
            //认证密码为null，验证userId
            subject.login(getToken(userId + "", ""));
        } catch (AuthenticationException e) {
            System.out.println("用户验证身份时出现异常:" + e);
        }
        return subject;
    }

    //外部通用方法-判断用户是否为某个角色
    public boolean isRole(long userId, String role) {
        //获取用户身份subject
        Subject subject = getSubject(userId);
        //判断角色
        boolean isRole = subject.hasRole(role);
        //登出
        subject.logout();
        return isRole;
    }

    //外部通用方法-判断用户是否拥有某个资源
    public boolean isPermitted(long userId, String permission) {
        //获取用户身份subject
        Subject subject = getSubject(userId);
        //判断是否拥有资源
        boolean isPermitted = subject.isPermitted(permission);
        //登出
        subject.logout();
        return isPermitted;
    }

    //外部通用方法-判断用户是否拥有多个资源
    public boolean[] isPermitted(long userId, String... permission) {
        //获取用户身份subject
        Subject subject = getSubject(userId);
        //判断是否拥有资源
        boolean[] isPermitted = subject.isPermitted(permission);
        //登出
        subject.logout();
        return isPermitted;
    }

}
