package com.singer.application.dto.sm;

public record SM02Response(
        String title,
        String text,
        String regdate,
        int seq
) {

    public String getTitle() {
        return title();
    }

    public String getText() {
        return text();
    }

    public String getRegdate() {
        return regdate();
    }

    public int getSeq() {
        return seq();
    }

}
