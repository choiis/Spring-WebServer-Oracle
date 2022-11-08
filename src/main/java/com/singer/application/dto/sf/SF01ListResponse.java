package com.singer.application.dto.sf;

import java.util.List;
import lombok.Value;

@Value
public class SF01ListResponse {

    List<SF01Response> list;

    int nowPage;

    int totCnt;
}
