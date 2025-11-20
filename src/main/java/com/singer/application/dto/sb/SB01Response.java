package com.singer.application.dto.sb;

import com.singer.common.util.Constants.YES_NO;

public record SB01Response(
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
        String video,
        String videopath,
        YES_NO videobool,
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

    public String getVideo() {
        return video();
    }

    public String getVideopath() {
        return videopath();
    }

    public YES_NO getVideobool() {
        return videobool();
    }

    public int getResult() {
        return result();
    }

}
