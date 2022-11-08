package com.singer.application.dto.sr;

import com.singer.common.exception.ExceptionMsg;
import javax.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class SR01Request {

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_1)
    String title;

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_2)
    String text;

    String userid;

    String markertitle;

    int grade;
    double mapx;
    double mapy;
    String regdate;

}
