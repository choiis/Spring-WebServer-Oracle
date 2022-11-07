package com.singer.application.dto.sf;

import java.util.List;
import lombok.Value;

@Value
public class SF02ListResponse {

    List<SF02Response> list;

    int parents;

    int nowPage;

    int totCnt;
}
