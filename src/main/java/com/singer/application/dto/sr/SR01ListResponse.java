package com.singer.application.dto.sr;

import java.util.List;

public record SR01ListResponse(
        List<SR01Response> list,
        int nowPage,
        int totCnt
) {

    public List<SR01Response> getList() {
        return list();
    }

    public int getNowPage() {
        return nowPage();
    }

    public int getTotCnt() {
        return totCnt();
    }

}
