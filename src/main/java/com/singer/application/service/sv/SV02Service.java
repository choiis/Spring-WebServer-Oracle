package com.singer.application.service.sv;

import com.singer.domain.vo.sv.SV02Vo;

public interface SV02Service {

	public int updateSV01Vo(SV02Vo sv02Vo) throws Exception;

	public int insertSv03Vo(SV02Vo sv02Vo, String userid) throws Exception;

}
