package com.singer.application.dto.sb;

public record SB02Response(
        int seq,
        int seq01,
        String userid,
        String text,
        int good,
        int reply,
        String regdate,
        boolean deleteYn
) {

    public int getSeq() {
        return seq();
    }

    public int getSeq01() {
        return seq01();
    }

    public String getUserid() {
        return userid();
    }

    public String getText() {
        return text();
    }

    public int getGood() {
        return good();
    }

    public int getReply() {
        return reply();
    }

    public String getRegdate() {
        return regdate();
    }

    public boolean getDeleteYn() {
        return deleteYn();
    }

}
