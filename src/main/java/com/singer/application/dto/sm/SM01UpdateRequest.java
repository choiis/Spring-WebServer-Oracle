package com.singer.application.dto.sm;

import com.singer.common.exception.ExceptionMsg;
import jakarta.validation.constraints.NotBlank;

public record SM01UpdateRequest(

        @NotBlank(message = ExceptionMsg.EXT_MSG_INPUT_1)
        String username,

        @NotBlank(message = ExceptionMsg.EXT_MSG_INPUT_2)
        String brth,

        String email,

        // 비밀번호는 선택. null 이면 변경하지 않음.
        String passwd
) {
}