package com.singer.application.dto.sb;

import java.util.List;

public record SB02ListResponse(
        List<SB02Response> list,
        int parents,
        int nowPage,
        int totCnt
) {

    public List<SB02Response> getList() {
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
