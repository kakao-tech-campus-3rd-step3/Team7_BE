package com.careerfit.auth.service;

import com.careerfit.auth.domain.CustomOAuth2User;
import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberFinder memberFinder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String providerId = String.valueOf(attributes.get(userNameAttributeName));

        Optional<Member> userOptional = memberFinder.getMemberWithOptional(registrationId,
            providerId);

        return userOptional.map(
                member -> new CustomOAuth2User(member, attributes, userNameAttributeName, false))
            .orElseGet(() -> new CustomOAuth2User(null, attributes, userNameAttributeName, true));
    }

}
