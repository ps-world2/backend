package com.pskjeong.psk.auth;

import com.pskjeong.psk.config.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class OAuth2SucessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 사용자 객체를 가져온다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 객체에서 kakao_acount를 가져온다.
        Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");

        // email을 가져온다.
        String url = makeRedirectUrl();

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }

        String accessToken = JwtTokenProvider.generateAccessToken(kakao_account);
        String refreshToken = JwtTokenProvider.generateRefreshToken();

        response.addCookie(new Cookie("psk_token", accessToken));
        response.addCookie(new Cookie("psk_refresh", refreshToken));

        // 클라이언트로 리다이렉트를 진행한다.
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl() {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/")
                .build().toUriString();
    }
}
