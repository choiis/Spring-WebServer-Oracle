package com.singer.application.dto.sf;

import com.singer.common.exception.ExceptionMsg;
import jakarta.validation.constraints.NotEmpty;

public record SF01Request(
        String title,
        String text,
        String userid,
        String regdate,
        String filename
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

    public String getFilename() {
        return filename();
    }

}
