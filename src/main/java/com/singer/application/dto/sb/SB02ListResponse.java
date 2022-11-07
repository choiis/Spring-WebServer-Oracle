package com.singer.application.dto.sb;

import java.util.List;
import lombok.Value;

@Value
public class SB02ListResponse {

    List<SB02Response> list;

    int parents;

    int nowPage;

    int totCnt;
}
