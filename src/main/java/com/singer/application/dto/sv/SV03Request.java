package com.singer.application.dto.sv;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SV03Request {

    private int seq;

    private int idx;

    private String userid;

    private String regdate;
}
