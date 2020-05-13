package com.spring.wxshop.service.impl;

import com.spring.wxshop.service.SmsCodeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroRealm extends AuthorizingRealm {
    private final SmsCodeService smsCodeService;

    @Autowired
    public ShiroRealm(SmsCodeService smsCodeService) {
        this.smsCodeService = smsCodeService;
        this.setCredentialsMatcher((token, info) -> getCredentialsWithToken(token).equals(info.getCredentials()));
    }

    private String getCredentialsWithToken(AuthenticationToken token) {
        return (String) token.getCredentials();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String tel = (String) token.getPrincipal();
        String code = smsCodeService.getCode(tel);
        return new SimpleAuthenticationInfo(tel, code, getName());
    }
}
