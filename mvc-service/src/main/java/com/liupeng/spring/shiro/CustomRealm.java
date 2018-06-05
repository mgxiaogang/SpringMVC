package com.liupeng.spring.shiro;

import java.util.ArrayList;
import java.util.List;

import com.liupeng.spring.shiro.vo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm
 *
 * @author fengdao.lp
 * @date 2018/6/5
 */
public class CustomRealm extends AuthorizingRealm {

    /**
     * 认证 - 执行currentUser.login(token);时候进入此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从 token 中获取用户身份信息
        String username = (String)token.getPrincipal();

        // 通过 username 从数据库中查询 ,此处模拟，省去查询
        User user = new User();
        // 名称
        user.setUserName(username);
        // 获取从数据库查询出来的用户密码，这里使用静态数据模拟
        user.setPassword("zhangsan");

        // 如果查询不到则返回 null，这里模拟查询不到
        if (!username.equals(user.getPassword())) {
            return null;
        }

        //返回认证信息由父类 AuthenticatingRealm 进行认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
            getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权 - 执行currentUser.hasRole或currentUser.isPermitted等判断权限方法时候进入此方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取身份信息
        User user = (User)principals.getPrimaryPrincipal();
        // 根据身份信息从数据库中查询权限数据
        // 这里使用静态数据模拟
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:*");
        permissions.add("department:*");

        // 将权限信息封闭为AuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 模拟数据，添加 manager 角色
        simpleAuthorizationInfo.addRole("manager");

        for (String permission : permissions) {
            simpleAuthorizationInfo.addStringPermission(permission);
        }

        return simpleAuthorizationInfo;
    }
}