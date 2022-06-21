package com.pskjeong.psk.auth.service;

import com.pskjeong.psk.members.domain.Members;
import com.pskjeong.psk.members.domain.MembersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MembersRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 현재 사용자 정보를 가져온다.
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("attributes : {}", attributes);

        // 이메일을 가져온다.
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakao_account.get("email");

        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickname = (String) properties.get("nickname");

        log.info(email);
        log.info(nickname);

        // 가입되어있는지 확인한다.
        if (!memberRepository.existsByMemberEmail(email)) {
            memberRepository.save(Members.builder().memberEmail(email).memberName(nickname).build());
        }

        // 스프링 시큐리티의 권한 코드는 항상 "ROLE_" 로 시작한다.
        // 추후에 .antMatchers({page}).hasRole("MEMBER")로 지정하면 해당 권한을 가진 친구만 접근할 수 있게 된다.
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")), attributes, "id");
    }
}
