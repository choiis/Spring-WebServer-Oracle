package com.singer.application.dto.sv;

import java.util.List;

public record SV01ListResponse(
        List<SV01Response> list,
        int nowPage,
        int totCnt
) {

    public List<SV01Response> getList() {
        return list();
    }

    public int getNowPage() {
        return nowPage();
    }

    public int getTotCnt() {
        return totCnt();
    }

}
