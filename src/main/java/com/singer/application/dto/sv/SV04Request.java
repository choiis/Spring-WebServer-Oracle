package com.singer.application.dto.sv;

import com.singer.common.exception.ExceptionMsg;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SV04Request {

    int seq01;

    int parents;

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_2)
    String text;

    int nowPage;

    String regdate;
}
