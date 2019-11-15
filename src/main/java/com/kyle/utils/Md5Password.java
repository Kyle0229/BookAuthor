package com.kyle.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Created by 54110 on 2019-09-20.
 */
public class Md5Password {

    public String password(String password, String salt){
        String hashAlgorithName = "MD5";//加密算法
        //String password = "admin";//登陆时的密码
        int hashIterations =1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);//使用登录名做为salt
        String simpleHash = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations).toHex();
        return simpleHash;
    }
}
