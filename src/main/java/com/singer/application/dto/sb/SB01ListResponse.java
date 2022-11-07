package com.singer.application.dto.sb;

import java.util.List;
import lombok.Value;

@Value
public class SB01ListResponse {

    List<SB01Response> list;

    int nowPage;

    int totCnt;
}
