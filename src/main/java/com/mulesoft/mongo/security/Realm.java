package com.mulesoft.mongo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.mulesoft.mongo.util.Utils;

public class Realm extends AuthorizingRealm {
    private static final String USER = "admin";
    private static final String PASSWD = "admin";

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userPasswordToken = (UsernamePasswordToken) token;
        String user = userPasswordToken.getUsername();
        String password = new String(userPasswordToken.getPassword());
        if (Utils.StringUtils.isNullOrEmpty(user) || Utils.StringUtils.isNullOrEmpty(password)
                || !USER.equalsIgnoreCase(user) || !PASSWD.equals(password)) {
            throw new AuthenticationException("Service cannot allow access with invalid credentials");
        }

        return new SimpleAuthenticationInfo(user, password, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Collection<String> principalsList = principals.byType(String.class);
        if (principalsList.isEmpty()) {
            throw new AuthorizationException("Empty principals list");
        }
        Set<String> roles = new HashSet<String>();
        roles.add("ops");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setRoles(roles);
        // Set<Permission> permissions = new HashSet<Permission>();
        // info.setObjectPermissions(permissions);

        return info;
    }
}
