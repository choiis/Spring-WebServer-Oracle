package com.singer.application.dto.sv;

import java.util.List;

public record SV04ListResponse(
        List<SV04Response> list,
        int parents,
        int nowPage,
        int totCnt
) {

    public List<SV04Response> getList() {
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
