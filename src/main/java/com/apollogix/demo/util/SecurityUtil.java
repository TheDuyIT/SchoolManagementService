package com.apollogix.demo.util;

import com.apollogix.demo.domain.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static UserInfo getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.getPrincipal() instanceof UserInfo) ?
                (UserInfo) authentication.getPrincipal() :
                null;
    }
}
