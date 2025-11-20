package com.singer.application.dto.sr;

import com.singer.common.exception.ExceptionMsg;
import jakarta.validation.constraints.NotEmpty;

public record SR01Request(
        String title,
        String text,
        String userid,
        String markertitle,
        int grade,
        double mapx,
        double mapy,
        String regdate
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

    public String getMarkertitle() {
        return markertitle();
    }

    public int getGrade() {
        return grade();
    }

    public double getMapx() {
        return mapx();
    }

    public double getMapy() {
        return mapy();
    }

    public String getRegdate() {
        return regdate();
    }

}
