package com.singer.application.dto.sv;

import javax.validation.constraints.NotEmpty;

import com.singer.common.exception.ExceptionMsg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SV02Request {

    private int idx;

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_9)
    private String content;
}
