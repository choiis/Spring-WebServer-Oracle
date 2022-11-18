package com.singer.application.dto.sm;

import java.util.List;
import lombok.Value;

@Value
public class SM02ListResponse {

	List<SM02Response> list;

	int nowPage;

	int totCnt;
}
