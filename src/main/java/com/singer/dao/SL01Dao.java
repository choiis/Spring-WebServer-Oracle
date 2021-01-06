package com.singer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.vo.SL01Vo;

@Repository("sl01Dao")
public class SL01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SL01";

	@SuppressWarnings("unchecked")
	public List<SL01Vo> selectSL01(SL01Vo sl01Vo) throws Exception {
		return (List<SL01Vo>) selectList(namespace + ".selectSL01", sl01Vo);
	}

	public int insertChatLog(SL01Vo sl01Vo) throws Exception {
		return insert(namespace + ".insertChatlog", sl01Vo);
	}
}
