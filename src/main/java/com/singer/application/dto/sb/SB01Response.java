package com.singer.application.dto.sb;

import com.singer.common.util.Constants.YES_NO;
import lombok.Value;

@Value
public class SB01Response {

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

    String video;

    String videopath;

    YES_NO videobool;

    protected int result;
}
