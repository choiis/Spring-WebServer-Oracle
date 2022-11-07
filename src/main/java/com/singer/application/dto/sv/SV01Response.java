package com.singer.application.dto.sv;

import java.util.List;
import lombok.Value;

@Value
public class SV01Response {

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

    int votedCnt;

    int multiselect;

    int votedYn;

    int totCnt;

    List<SV02Response> sv02Vos;

    int result;
}
