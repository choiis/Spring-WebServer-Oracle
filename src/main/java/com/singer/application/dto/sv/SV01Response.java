package com.singer.application.dto.sv;

import java.util.List;

public record SV01Response(
        int seq,
        String title,
        String text,
        String userid,
        int good,
        int hit,
        String showDate,
        String regdate,
        int reply,
        boolean deleteYn,
        String goodlog,
        String hatelog,
        int votedCnt,
        int multiselect,
        int votedYn,
        int totCnt,
        List<SV02Response> sv02Vos,
        int result
) {

    public int getSeq() {
        return seq();
    }

    public String getTitle() {
        return title();
    }

    public String getText() {
        return text();
    }

    public String getUserid() {
        return userid();
    }

    public int getGood() {
        return good();
    }

    public int getHit() {
        return hit();
    }

    public String getShowDate() {
        return showDate();
    }

    public String getRegdate() {
        return regdate();
    }

    public int getReply() {
        return reply();
    }

    public boolean getDeleteYn() {
        return deleteYn();
    }

    public String getGoodlog() {
        return goodlog();
    }

    public String getHatelog() {
        return hatelog();
    }

    public int getVotedCnt() {
        return votedCnt();
    }

    public int getMultiselect() {
        return multiselect();
    }

    public int getVotedYn() {
        return votedYn();
    }

    public int getTotCnt() {
        return totCnt();
    }

    public List<SV02Response> getSv02Vos() {
        return sv02Vos();
    }

    public int getResult() {
        return result();
    }

}
