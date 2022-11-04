package com.singer.domain.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuperDao {

    @Autowired
    private SqlSession sqlSession;

    @SuppressWarnings("rawtypes")
    protected List selectList(String statement, Object vo) {
        return sqlSession.selectList(statement, vo);
    }

    protected Object selectOne(String statement) {
        return sqlSession.selectOne(statement);
    }

    protected Object selectOne(String statement, Object vo) {
        return sqlSession.selectOne(statement, vo);
    }

    protected int selectCnt(String statement, Object vo) {
        return sqlSession.selectOne(statement, vo);
    }

    @SuppressWarnings("rawtypes")
    protected List selectList(String statement) {
        return sqlSession.selectList(statement);
    }

    protected int update(String statement, Object vo) {
        return sqlSession.update(statement, vo);
    }

    protected int insert(String statement, Object vo) {
        return sqlSession.insert(statement, vo);
    }

    protected int delete(String statement, Object vo) {
        return sqlSession.delete(statement, vo);
    }
}
