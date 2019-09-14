package com.singer.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.vo.SB01Vo;

public interface SB01Service {
	public int insertSB01Vo(SB01Vo sb01Vo, MultipartHttpServletRequest request) throws Exception;

	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception;

	public List<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo) throws Exception;

	public SB01Vo selectOneSB01Vo(SB01Vo sb01Vo, String userid) throws Exception;

	public int updateSB01Vo(SB01Vo sb01Vo) throws Exception;

	public SB01Vo likeSB01Vo(SB01Vo sb01Vo, String sessionid) throws Exception;

	public SB01Vo hateSB01Vo(SB01Vo sb01Vo, String sessionid) throws Exception;

	public int deleteSB01Vo(SB01Vo sb01Vo) throws Exception;

	public InputStream selectVideo(SB01Vo sb01Vo, HttpServletRequest request) throws Exception;
}
