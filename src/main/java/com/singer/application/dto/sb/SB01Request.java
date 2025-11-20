package com.singer.application.dto.sb;

import com.singer.common.exception.ExceptionMsg;
import com.singer.common.util.Constants.YES_NO;
import jakarta.validation.constraints.NotEmpty;

public record SB01Request(
        String title,
        String text,
        String userid,
        String regdate,
        String video,
        String videopath,
        YES_NO videobool
) {

    public String getTitle() {
        return title();
    }

    public String getText() {
        return text();
    }

    public String getUserid() {
        return userid();
    }

    public String getRegdate() {
        return regdate();
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

}
