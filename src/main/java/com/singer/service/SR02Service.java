package com.singer.service;

import com.singer.vo.SR01Vo;

public interface SR02Service {

	public int insertSR02Vo(SR01Vo sr01Vo, String sessionid) throws Exception;

	public SR01Vo selectOneSR02Vo(SR01Vo sr01Vo, String userid) throws Exception;

	public int deleteSR02Vo(SR01Vo sr01Vo, String sessionid) throws Exception;

}
