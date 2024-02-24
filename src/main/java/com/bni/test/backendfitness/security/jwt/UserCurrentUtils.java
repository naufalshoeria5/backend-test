package com.bni.test.backendfitness.security.jwt;

import com.bni.test.backendfitness.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserCurrentUtils {

    public static String getCurrentUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl currentUser = (UserDetailsImpl) auth.getPrincipal();

        return currentUser.getId();
    }
}
