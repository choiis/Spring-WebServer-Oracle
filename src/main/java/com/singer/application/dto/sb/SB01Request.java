package com.singer.application.dto.sb;

import com.singer.common.exception.ExceptionMsg;
import com.singer.common.util.Constants.YES_NO;
import javax.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class SB01Request {

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_1)
    String title;

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_2)
    String text;

    String userid;


    String regdate;

    String video;

    String videopath;

    YES_NO videobool;
}
