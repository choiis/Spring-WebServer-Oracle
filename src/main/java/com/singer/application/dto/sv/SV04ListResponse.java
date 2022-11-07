package com.singer.application.dto.sv;

import java.util.List;
import lombok.Value;

@Value
public class SV04ListResponse {

    List<SV04Response> list;

    int parents;

    int nowPage;

    int totCnt;
}
