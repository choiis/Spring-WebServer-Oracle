package com.singer.application.dto.sv;

import java.util.List;
import lombok.Value;

@Value
public class SV01ListResponse {

    List<SV01Response> list;

    int nowPage;

    int totCnt;
}
