package com.careerfit.auth.service;

import com.careerfit.auth.domain.CustomUserDetails;
import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberFinder memberFinder;

    public CustomUserDetailsService(MemberFinder memberFinder) {
        this.memberFinder = memberFinder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberFinder.getMemberOrThrow(Long.parseLong(username));

        return new CustomUserDetails(member);
    }
}
