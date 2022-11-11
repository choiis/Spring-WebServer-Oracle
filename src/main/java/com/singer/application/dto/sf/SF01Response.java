package com.singer.application.dto.sf;

import lombok.Value;

@Value
public class SF01Response {

    int seq;

    String title;
    String text;

    String userid;

    int good;

    int hit;

    String showDate;
    String regdate;

    int reply;

    boolean deleteYn;
    String goodlog;

    String hatelog;

    String filename;

    int downcnt;

    int result;
}
