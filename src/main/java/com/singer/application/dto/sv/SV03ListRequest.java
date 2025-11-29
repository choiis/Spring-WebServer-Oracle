package com.singer.application.dto.sv;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class SV03ListRequest {

    List<SV03Request> list;


}
