package com.singer.application.dto.sb;

import java.util.List;

public record SB01ListResponse(
        List<SB01Response> list,
        int nowPage,
        int totCnt
) {

    public List<SB01Response> getList() {
        return list();
    }

    public int getNowPage() {
        return nowPage();
    }

    public int getTotCnt() {
        return totCnt();
    }

}
