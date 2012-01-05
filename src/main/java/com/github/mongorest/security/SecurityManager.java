package com.github.mongorest.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityManager extends DefaultWebSecurityManager {
    private static Logger logger = LoggerFactory.getLogger(SecurityManager.class);

    @Override
    protected void onSuccessfulLogin(AuthenticationToken token, AuthenticationInfo info, Subject subject) {
        String username = (String) token.getPrincipal();
        String address = null;

        HttpServletRequest request = (HttpServletRequest) ((WebSubject) subject).getServletRequest();
        if (request != null) {
            address = request.getHeader("X-Real-IP");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Service user=" + username + " successfully logged in from " + address);
        }
        super.onSuccessfulLogin(token, info, subject);
    }
}