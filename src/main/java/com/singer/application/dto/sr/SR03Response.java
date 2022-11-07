package com.singer.application.dto.sr;

import lombok.Value;

@Value
public class SR03Response {

    int seq;

    int seq01;
    String userid;
    String text;
    int good;
    int reply;

    protected String regdate;

    boolean deleteYn;

}
