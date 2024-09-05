package com.th.hab.user;

import com.th.hab.Repository.UserRepository;
import com.th.hab.user.model.UserSignInDto;
import com.th.hab.user.model.UserSignInVo;
import com.th.hab.user.model.UserSignUpDto;
import com.th.hab.common.AppProperties;
import com.th.hab.common.Const;
import com.th.hab.common.MyCookieUtils;
import com.th.hab.entity.User;
import com.th.hab.exception.AuthErrorCode;
import com.th.hab.exception.RestApiException;
import com.th.hab.response.ApiResponse;
import com.th.hab.response.ResVo;
import com.th.hab.security.AuthenticationFacade;
import com.th.hab.security.JwtTokenProvider;
import com.th.hab.security.MyPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.th.hab.common.Const.rtName;
import static com.th.hab.exception.AuthErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSerivce {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final MyCookieUtils myCookieUtils;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public ApiResponse<?> postSignUp(UserSignUpDto dto) {
        Optional<User> uidCheck = userRepository.findByUid(dto.getUid());
        if (uidCheck.isPresent()) {
            throw new RestApiException(DUPLICATED_UID);
        }
        Optional<User> emailCheck = userRepository.findByEmail(dto.getEmail());
        if (emailCheck.isPresent()) {
            throw new RestApiException(DUPLICATED_EMAIL);
        }
        String hashedUpw = passwordEncoder.encode(dto.getUpw());
        User user = new User();
        user.setNm(dto.getNm());
        user.setUpw(hashedUpw);
        user.setUid(dto.getUid());
        user.setEmail(dto.getEmail());

        userRepository.save(user);

        return new ApiResponse<>(null);
    }

    //로그인
    public UserSignInVo postSignIn(HttpServletResponse res, UserSignInDto dto) {
        Optional<User> optEntity = userRepository.findByUid(dto.getUid());
        User entity = optEntity.orElseThrow(() -> new RestApiException(AuthErrorCode.LOGIN_FAIL));
        if (!passwordEncoder.matches(dto.getUpw(), entity.getUpw())) {
            throw new RestApiException(AuthErrorCode.LOGIN_FAIL);
        }

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(entity.getIuser().intValue())
                .build();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);

        int rtCookieMaxAge = appProperties.getJwt().getRefreshCookieMaxAge();
        myCookieUtils.deleteCookie(res, rtName);
        myCookieUtils.setCookie(res, rtName, rt, rtCookieMaxAge);

        return UserSignInVo.builder()
                .accessToken(at)
                .build();
    }
    //로그 아웃
    public ResVo signout(HttpServletResponse res, HttpServletRequest req) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(req, res, SecurityContextHolder.getContext().getAuthentication());
        myCookieUtils.deleteCookie(res, "accessToken");
        myCookieUtils.deleteCookie(res, "refreshToken");
        return new ResVo(Const.SUCCESS);
    }
}
