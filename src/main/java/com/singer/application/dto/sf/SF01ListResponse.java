package com.singer.application.dto.sf;

import java.util.List;

public record SF01ListResponse(
        List<SF01Response> list,
        int nowPage,
        int totCnt
) {

    public List<SF01Response> getList() {
        return list();
    }

    public int getNowPage() {
        return nowPage();
    }

    public int getTotCnt() {
        return totCnt();
    }

}
