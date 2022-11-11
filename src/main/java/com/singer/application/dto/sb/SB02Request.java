package com.singer.application.dto.sb;

import com.singer.common.exception.ExceptionMsg;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SB02Request {

    int seq01;

    int parents;

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_2)
    String text;

    int nowPage;

    String regdate;
}
