package com.singer.application.dto.sm;

public record SMI1Response(
        String username,
        String userid,
        String brth,
        String regdate,
        String pfnum,
        String pcnum,
        String pbnum,
        String email
) {

    public String getUsername() {
        return username();
    }

    public String getUserid() {
        return userid();
    }

    public String getBrth() {
        return brth();
    }

    public String getRegdate() {
        return regdate();
    }

    public String getPfnum() {
        return pfnum();
    }

    public String getPcnum() {
        return pcnum();
    }

    public String getPbnum() {
        return pbnum();
    }

    public String getEmail() {
        return email();
    }

}
