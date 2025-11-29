package com.singer.application.controller.comm;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.dao.sm.SM01Dao;
import com.singer.domain.entity.sm.SM01Entity;
import com.singer.infrastructure.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증/인가 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SM01Dao sm01Dao;
    private final JwtService jwtService;

    public AuthController(SM01Dao sm01Dao,
                          JwtService jwtService) {
        this.sm01Dao = sm01Dao;
        this.jwtService = jwtService;
    }

    @Operation(summary = "로그인", description = "아이디/비밀번호로 로그인 후 JWT 액세스 토큰 발급")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws Exception {

        // 1) username → SM01.userid 로 사용
        SM01Entity param = new SM01Entity();
        param.setUserid(request.username());

        SM01Entity user = sm01Dao.selectLoginSM01Vo(param);

        // 2) 유저가 없거나 비밀번호가 다르면 401 + 메시지
        if (user == null || !user.getPasswd().equals(request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, "Your ID or password is incorrect."));
        }

        // 3) SM01.usertype / grade 에서 USER_CODE 추출
        USER_CODE userType = user.getUsertype();
        if (userType == null) {
            userType = USER_CODE.NORMAL; // 기본값
        }

        // 4) userid + userType 으로 JWT 토큰 생성
        String accessToken = jwtService.generateToken(user.getUserid(), userType);

        return ResponseEntity.ok(new LoginResponse(accessToken, "OK"));
    }

    public record LoginRequest(
            @NotBlank String username,
            @NotBlank String password
    ) {}

    public record LoginResponse(
            String accessToken,
            String message
    ) {}
}
