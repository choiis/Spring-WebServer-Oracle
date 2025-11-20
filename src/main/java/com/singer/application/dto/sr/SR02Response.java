package com.singer.application.dto.sr;

public record SR02Response(
        int seq,
        String userid,
        double grade,
        String regdate,
        int result
) {

    public int getSeq() {
        return seq();
    }

    public String getUserid() {
        return userid();
    }

    public double getGrade() {
        return grade();
    }

    public String getRegdate() {
        return regdate();
    }

    public int getResult() {
        return result();
    }

}
