package com.singer.application.dto.sr;

import lombok.Value;

@Value
public class SR01Response {

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

    String markertitle;

    double mapx;

    double mapy;
    double avggrade;

    int photocnt;
    int result;
}
