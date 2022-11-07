package com.singer.application.dto.sb;

import lombok.Value;

@Value
public class SB02Response {

    int seq;

    int seq01;
    String userid;
    String text;
    int good;
    int reply;

    protected String regdate;

    boolean deleteYn;

}
