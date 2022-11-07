package com.singer.application.dto.sv;

import lombok.Value;

@Value
public class SV04Response {

    int seq;

    int seq01;
    String userid;
    String text;
    int good;
    int reply;

    protected String regdate;

    boolean deleteYn;

}
