package com.singer.application.dto.sr;

import java.util.List;
import lombok.Value;

@Value
public class SR03ListResponse {

    List<SR03Response> list;

    int parents;

    int nowPage;

    int totCnt;
}
