package com.niteupad.retroBoard.util;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
there is AuditAware implementation that makes use of SecurityContextHolder, which is
responsible for holding the Authentication object for a logged in user. The principal
from the Authentication object is retrieved to get the username of the logged in user,
which will be persisted with a Comment object.
 */

@Component
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        return Optional.of(((User) authentication.getPrincipal()).getUsername());
    }
}