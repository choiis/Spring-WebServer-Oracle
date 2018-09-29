package com.singer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.vo.SM02Vo;

@Repository("sm02Dao")
public class SM02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SM02";

	public int insertSM02Vo(SM02Vo sm02Vo) throws Exception {
		return insert(namespace + ".insert", sm02Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SM02Vo> selectSM02Vo(SM02Vo sm02Vo) throws Exception {
		return (List<SM02Vo>) selectList(namespace + ".select", sm02Vo);
	}

	public SM02Vo selectOneSM02Vo(SM02Vo sm02Vo) throws Exception {
		return (SM02Vo) selectOne(namespace + ".selectOne", sm02Vo);
	}

	public int deleteSM02Vo(SM02Vo sm02Vo) throws Exception {
		return delete(namespace + ".delete", sm02Vo);
	}

	public int updateSM02Vo(SM02Vo sm02Vo) throws Exception {
		return update(namespace + ".update", sm02Vo);
	}

}
