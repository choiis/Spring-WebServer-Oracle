package com.singer.application.dto.sr;

import com.singer.common.exception.ExceptionMsg;
import javax.validation.constraints.NotEmpty;
import lombok.Value;

@Value
public class SR02Request {

    int seq;
    double grade;
}
