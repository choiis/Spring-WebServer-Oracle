package com.singer.domain.dao.sm;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.singer.domain.entity.sm.SM02Entity;

@Mapper
public interface SM02Dao {

	@Insert("insert into SM02 (seq,userid, title, text, regdate) values (SEQ_SM02.NEXTVAL ,#{userid}, #{title}, #{text}, #{regdate}) ")
	public int insertSM02(SM02Entity sm02Entity);

	@Select("select T.* from\r\n" + "				(select S.*,\r\n" + "   				 rownum as rown\r\n"
			+ "					from\r\n" + "					(select /*+ index_desc(SM02 IDX_SM02_1)*/\r\n"
			+ "               	 	seq as seq,\r\n" + "					userid as userid,\r\n"
			+ "					title as title,\r\n" + "					text as text, \r\n"
			+ "					regdate as regdate,\r\n" + "					count(*) over() as totCnt\r\n"
			+ "			 		from SM02 \r\n" + " 					where userid = #{userid}\r\n"
			+ "					) S where rownum <= #{endRownum}\r\n"
			+ "        		) T where T.rown >= #{startRownum} ")
	public List<SM02Entity> selectSM02(SM02Entity sm02Entity);

	@Select("select * from SM02 where userid = #{userid} and text = #{text} and regdate = #{regdate}")
	public SM02Entity selectOneSM02(SM02Entity sm02Entity);

	@Delete("delete from SM02 where seq = #{seq} and userid = #{userid}")
	public int deleteSM02(SM02Entity sm02Entity);

	@Update("update SM02  set title = #{title}, text = #{text} where userid  = #{userid} ")
	public int updateSM02(SM02Entity sm02Entity);
}
