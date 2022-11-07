package com.singer.application.dto.sf;

import lombok.Value;

@Value
public class SF02Response {

    int seq;

    int seq01;
    String userid;
    String text;
    int good;
    int reply;

    protected String regdate;

    boolean deleteYn;

}
