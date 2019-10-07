package com.singer.service;

import java.util.List;

import com.singer.vo.SR03Vo;

public interface SR03Service {

	public int insertSR03Vo(SR03Vo sr03Vo) throws Exception;

	public int likeSR03Vo(SR03Vo sr03Vo) throws Exception;

	public List<SR03Vo> selectSR03Vo(SR03Vo sr03Vo, String userid) throws Exception;

	public int updateSR03Vo(SR03Vo sr03Vo) throws Exception;

	public int deleteSR03Vo(SR03Vo sr03Vo) throws Exception;
}
