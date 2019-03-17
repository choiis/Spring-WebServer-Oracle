package com.singer.dao;

import org.springframework.stereotype.Repository;

import com.singer.vo.ErrorVo;

@Repository("errorDao")
public class ErrorDao extends SuperDao {

	private static final String namespace = "com.singer.mappers.Comm";

	public int insertError(ErrorVo errorVo) {
		return insert(namespace + ".insertError", errorVo);
	}

}
