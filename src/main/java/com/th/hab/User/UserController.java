package com.th.hab.User;

import com.th.hab.User.model.UserSignInDto;
import com.th.hab.User.model.UserSignInVo;
import com.th.hab.User.model.UserSignUpDto;
import com.th.hab.response.ApiResponse;
import com.th.hab.response.ResVo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserSerivce service;

    @PostMapping("/sign-in")
    public ApiResponse<UserSignInVo> postSignIn(HttpServletResponse res, @Valid @RequestBody UserSignInDto dto) {
        return new ApiResponse<>(service.postSignIn(res, dto));
    }

    @PostMapping("/sign-up")
    public ApiResponse<?> postSignUp(@Valid @RequestBody UserSignUpDto dto) {
        return service.postSignUp(dto);
    }

    @PostMapping("/signout")
    public ApiResponse<ResVo> postSignout(HttpServletResponse res) {
        return new ApiResponse<>(service.signout(res));
    }
}
