package cn.anyongliang.shiro;

import cn.anyongliang.db.jdbc.SqlTable;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * create by anyonglinag 2019-5-24
 * shiro的核心之一
 * 实现AuthorizingRealm,摒弃IniRealm的配置文件方式和JDBCRealm这种自带的Realm
 * 改用兼容cloud.file的数据库连接方式
 * 可以理解为一个专门为本项目写的实现获取shiro框架中Realm域的工具类
 */
public class DatabaseRealm extends AuthorizingRealm {

    public static DatabaseRealm VOID() {
        return new DatabaseRealm();
    }

    /**
     * 获取账号的角色、资源
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //能进入到这里，表示账号已经通过验证了
        String userName = (String) principalCollection.getPrimaryPrincipal();

        //实际上username就是userId (获取)  这里用username userid password 随意，接下来取出资源，角色需要自己去实现
        long userId = Long.parseLong(userName);

        //获取该账户拥有的角色（绑定的+继承的） 这里自己实现
        //Set<String> setRoles = getRolesByUserId(userId);
        //获取该账户拥有的资源（user本身绑定的+拥有角色绑定的+拥有的角色的策略组绑定的） 这里自己实现
        //Set<String> setPermissions = getPermissionsByUserId(userId);

        //授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //把获取到的角色和资源放进去
        //info.setStringPermissions(setPermissions);
        //info.setRoles(setRoles);
        return info;
    }

    /**
     * 根据token验证账户登录,由于我们的登录是用redis和游览器的cookie做的，所以简单验证下user就行
     *
     * @param token
     * @return 认证信息
     * @throws AuthenticationException 如果登录失败，抛出这个错误
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //根据token获取认证的账号密码
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = token.getPrincipal().toString();
        //简单验证，password就不需要了，随意填写
        String password = new String(usernamePasswordToken.getPassword());
        //判断数据库中是存在该用户
        long count = SqlTable.use().queryObject("SELECT count(*) as count FROM `user` WHERE state = 0 AND user_id = ?", new Object[]{userName}).getLong("count");
        //如果为空就是账号不存在，如果不相同就是密码错误，但是都抛出AuthenticationException，而不是抛出具体错误原因，免得给破解者提供帮助信息
        if (count == 0L) {
            System.out.println("权限登录认证出现错误");
            throw new AuthenticationException();
        }
        //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, password, getName());
        return info;
    }
}