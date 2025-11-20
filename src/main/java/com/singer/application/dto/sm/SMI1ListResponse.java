package com.singer.application.dto.sm;

import java.util.List;

public record SMI1ListResponse(
        List<SMI1Response> list
) {

    public List<SMI1Response> getList() {
        return list();
    }

}
