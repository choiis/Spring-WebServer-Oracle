package com.singer.dao;

import org.springframework.stereotype.Repository;

import com.singer.vo.SR01Vo;

@Repository("sr02Dao")
public class SR02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR02";

	public int insertSR02Vo(SR01Vo sr01Vo) throws Exception {
		return insert(namespace + ".insert", sr01Vo);
	}

	public int deleteSR02Vo(SR01Vo sr01Vo) throws Exception {
		return delete(namespace + ".delete", sr01Vo);
	}

	public SR01Vo selectOneSR02Vo(SR01Vo sr01Vo) throws Exception {
		return (SR01Vo) selectOne(namespace + ".selectOne", sr01Vo);
	}

	public SR01Vo selectGradeSR02Vo(SR01Vo sr01Vo) throws Exception {
		return (SR01Vo) selectOne(namespace + ".selectGrade", sr01Vo);
	}
}