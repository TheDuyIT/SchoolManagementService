package com.apollogix.demo.config;

import com.apollogix.demo.constant.Constants;
import com.apollogix.demo.domain.UserInfo;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserContextAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = Optional.ofNullable(authentication).map((auth) -> (UserInfo) auth.getPrincipal());
        return user
                .map(UserInfo::getEmail)
                .or(() -> Optional.of(Constants.SYSTEM));
    }
}
