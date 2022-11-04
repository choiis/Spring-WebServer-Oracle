package com.singer.domain.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.singer.domain.vo.CommVo;

@Repository("commDao")
public class CommDao extends SuperDao {

	private static final String namespace = "com.singer.mappers.Comm";

	@SuppressWarnings("unchecked")
	public List<CommVo> selectCode(CommVo commVo) throws Exception {
		return (List<CommVo>) selectList(namespace + ".selectCode", commVo);
	}

	@SuppressWarnings("unchecked")
	public List<CommVo> selectMenu(CommVo commVo) throws Exception {
		return (List<CommVo>) selectList(namespace + ".selectMenu", commVo);
	}

	@SuppressWarnings("unchecked")
	@Cacheable(value = "menuCacheData")
	public List<CommVo> selectAllMenu() throws Exception {
		return (List<CommVo>) selectList(namespace + ".selectAllMenu");
	}

	@SuppressWarnings("unchecked")
	public List<CommVo> selectCodeGrp(CommVo commVo) throws Exception {
		return (List<CommVo>) selectList(namespace + ".selectCodeGrp", commVo);
	}

	public String checkCreateUser(CommVo commVo) throws Exception {
		return (String) selectOne(namespace + ".checkCreateUser", commVo);
	}

	public int insertMenu(CommVo commVo) throws Exception {
		return insert(namespace + ".insertMenu", commVo);
	}

	public int deleteMenu(CommVo commVo) throws Exception {
		return insert(namespace + ".deleteMenu", commVo);
	}

	public int updateMenu(CommVo commVo) throws Exception {
		return insert(namespace + ".updateMenu", commVo);
	}

	public int insertCode(CommVo commVo) throws Exception {
		return insert(namespace + ".insertCode", commVo);
	}

	public int deleteCode(CommVo commVo) throws Exception {
		return insert(namespace + ".deleteCode", commVo);
	}

	public int updateCode(CommVo commVo) throws Exception {
		return insert(namespace + ".updateCode", commVo);
	}

}
