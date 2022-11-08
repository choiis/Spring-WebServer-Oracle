package com.singer.application.dto.sr;

import java.util.List;
import lombok.Value;

@Value
public class SR01ListResponse {

    List<SR01Response> list;

    int nowPage;

    int totCnt;
}
