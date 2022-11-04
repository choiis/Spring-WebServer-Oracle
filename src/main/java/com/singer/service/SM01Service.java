package com.singer.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.vo.SM01Vo;

public interface SM01Service {

    public HashMap<String, Object> insertSM01Vo(SM01Vo sm01Vo, MultipartHttpServletRequest request) throws Exception;

    public List<SM01Vo> selectSM01Vo(SM01Vo sm01Vo) throws Exception;

    public SM01Vo selectOneSM01Vo(SM01Vo sm01Vo) throws Exception;

    public SM01Vo login(SM01Vo sm01Vo) throws Exception;

    public int deleteSM01Vo(SM01Vo sm01Vo) throws Exception;

    public SM01Vo updateSM01Vo(SM01Vo sm01Vo, MultipartHttpServletRequest request, String userId) throws Exception;

    public InputStream selectImage(SM01Vo sm01Vo, HttpServletRequest request) throws Exception;

    public int updateSME1Vo(SM01Vo sm01Vo, String userId) throws Exception;
}