package com.singer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.singer.vo.SM01Vo;

public interface SM01Service {

	public HashMap<String, Object> insertSM01Vo(SM01Vo sm01Vo) throws Exception;

	public List<SM01Vo> selectSM01Vo(SM01Vo sm01Vo) throws Exception;

	public SM01Vo selectOneSM01Vo(SM01Vo sm01Vo) throws Exception;

	public int deleteSM01Vo(SM01Vo sm01Vo) throws Exception;

	public SM01Vo updateSM01Vo(SM01Vo sm01Vo) throws Exception;

	public int insertImage(Map<String, Object> hashMap) throws Exception;

	public HashMap<String, Object> selectImage(SM01Vo sm01Vo) throws Exception;
}