package com.careerfit.auth.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.careerfit.member.domain.Member;

import lombok.Getter;

@Getter
public class CustomOAuth2User implements OAuth2User {

    private Member member;
    private Map<String, Object> attributes;
    private String userNameAttributeName;
    private boolean isNewUser;

    /*
    일반 로그인용
     */
    public CustomOAuth2User(Member member, String userNameAttributeName) {
        this.member = member;
        this.userNameAttributeName = userNameAttributeName;
        this.isNewUser = false;
    }

    /*
    소셜 로그인용
     */
    public CustomOAuth2User(Member member, Map<String, Object> attributes, String userNameAttributeName, boolean isNewUser) {
        this.member = member;
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
        this.isNewUser = isNewUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (member == null) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST"));
        }
        return Collections.singleton(new SimpleGrantedAuthority(member.getMemberRole().getRole()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get(this.userNameAttributeName));
    }
}