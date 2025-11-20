package com.singer.application.dto.sf;

import java.util.List;

public record SF02ListResponse(
        List<SF02Response> list,
        int parents,
        int nowPage,
        int totCnt
) {

    public List<SF02Response> getList() {
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
