package com.singer.application.dto.sr;

import java.util.List;

public record SR03ListResponse(
        List<SR03Response> list,
        int parents,
        int nowPage,
        int totCnt
) {

    public List<SR03Response> getList() {
        return list();
    }

    public int getParents() {
        return parents();
    }

    public int getNowPage() {
        return nowPage();
    }

    public int getTotCnt() {
        return totCnt();
    }

}
