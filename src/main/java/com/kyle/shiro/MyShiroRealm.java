package com.kyle.shiro;

import com.kyle.dao.AuthorRespository;
import com.kyle.domain.Author;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * Created by 54110 on 2019-09-19.
 */
public class MyShiroRealm extends AuthorizingRealm {


    @Resource
    private AuthorRespository authorRespository;
    /* @Resource
     private SysPermissionMapper sysPermissionMapper;*/
    /*
     * 负责授权的方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
//        String loginName = (String) principal.getPrimaryPrincipal();
//        //查找权限
//        List<SysPermission> sysPermissions = sysPermissionRepository.selectPermissionByLoginName(loginName);
//        Collection permissions = new HashSet<>(); //去重
//
//        for (SysPermission s : sysPermissions) {
//            permissions.add(s.getPerName());  //获得权限名
//        }
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//
//        simpleAuthorizationInfo.addStringPermissions(permissions);

//        return simpleAuthorizationInfo;
        return null;
    }

    /*
     *负责用户认证的方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       // String aname = (String) token.getPrincipal();
        String aphone = (String) token.getPrincipal();
        Author author = authorRespository.findByAphone(aphone);
        String aname = author.getAname();
        String salt = aname;  //把用户名作为加密密码的盐
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(aphone,author.getApassword(), ByteSource.Util.bytes(salt), getName());
        return simpleAuthenticationInfo;
    }
}