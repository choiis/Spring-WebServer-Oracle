package com.singer.application.dto.sr;

public record SR01Response(
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
        String markertitle,
        double mapx,
        double mapy,
        double avggrade,
        int photocnt,
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

    public String getMarkertitle() {
        return markertitle();
    }

    public double getMapx() {
        return mapx();
    }

    public double getMapy() {
        return mapy();
    }

    public double getAvggrade() {
        return avggrade();
    }

    public int getPhotocnt() {
        return photocnt();
    }

    public int getResult() {
        return result();
    }

}
