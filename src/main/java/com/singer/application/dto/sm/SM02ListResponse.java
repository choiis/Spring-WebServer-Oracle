package com.singer.application.dto.sm;

import java.util.List;

public record SM02ListResponse(
        List<SM02Response> list,
        int nowPage,
        int totCnt
) {

    public List<SM02Response> getList() {
        return list();
    }

    public int getNowPage() {
        return nowPage();
    }

    public int getTotCnt() {
        return totCnt();
    }

}
