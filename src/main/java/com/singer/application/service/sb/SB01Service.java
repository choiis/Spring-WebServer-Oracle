package com.singer.application.service.sb;

import com.singer.application.dto.sb.SB01Composer;
import com.singer.application.dto.sb.SB01ListResponse;
import com.singer.application.dto.sb.SB01Request;
import com.singer.application.dto.sb.SB01Response;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.exception.AppException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.infrastructure.config.S3Properties;
import com.singer.infrastructure.util.S3Util;
import com.singer.common.util.CommonUtil;
import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.common.util.Constants.YES_NO;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sb.SB01Dao;
import com.singer.domain.dao.sb.SB02Dao;
import com.singer.domain.entity.sb.SB01Vo;
import com.singer.domain.entity.sb.SB02Vo;

@Service
public class SB01Service {

    @Autowired
    private SB01Dao sb01Dao;

    @Autowired
    private SB02Dao sb02Dao;

    @Autowired
    S3Util s3Util;

    @Autowired
    private S3Properties s3Properties;

    @Transactional(rollbackFor = {Exception.class})
    public SB01Response insertSB01Vo(SB01Request sb01Request, MultipartHttpServletRequest request, String userid)
        throws Exception {

        SB01Vo sb01Vo = SB01Composer.requestToentity(sb01Request, userid);
        MultipartFile video = null;
        Iterator<String> itr = request.getFileNames();

        if (ObjectUtils.isEmpty(itr)) {
            throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
        }
        while (itr.hasNext()) {
            video = request.getFile(itr.next());
        }
        String timestamp = DateUtil.getTodayTime();
        StringBuilder sb = new StringBuilder("video/" + timestamp);
        sb01Vo.setRegdate(timestamp);

        if (CommonUtil.chkVideoFile(video.getOriginalFilename())) {
            sb01Vo.setVideobool(YES_NO.YES);
            sb.append(".mp4");
        } else if (CommonUtil.chkAudioFile(video.getOriginalFilename())) {
            sb01Vo.setVideobool(YES_NO.NO);
            sb.append(".mp3");
        } else {
            throw new AppException(ExceptionMsg.EXT_MSG_INPUT_5);
        }

        String path = s3Properties.getTempPath();
        File file = new File(path + "/" + sb.toString());
        video.transferTo(file);

        s3Util.putS3File(sb.toString(), file);

        sb01Dao.insertSB01Vo(sb01Vo);

        SB01Vo sb01Vo2 = new SB01Vo();
        sb01Vo2.setSeq(sb01Vo.getSeq());
        sb01Vo2.setRegdate(DateUtil.getToday());
        sb01Vo2.setVideopath(sb.toString());
        file.delete();
        int success = sb01Dao.insertVideo(sb01Vo2);
        sb01Vo.setResult(success);
        return SB01Composer.entityToResponse(sb01Vo);

    }

    public SB01ListResponse selectSB01Vo(int nowPage) throws Exception {

        SB01Vo sb01Vo = new SB01Vo();
        sb01Vo.setNowPage(nowPage);
        List<SB01Vo> list = sb01Dao.selectSB01Vo(sb01Vo);
        SB01Vo vo = sb01Dao.selectSB01VoCount();
        int totalCount = ObjectUtils.isEmpty(vo) ? 0 : CommonUtil.getPageCnt(vo.getTotCnt());

        return SB01Composer.entityListToResponse(list, nowPage, totalCount);
    }



    @Transactional(rollbackFor = {Exception.class})
    public SB01Response selectOneSB01Vo(int seq, String userid) throws Exception {

        SB01Vo sb01Vo = new SB01Vo();
        sb01Vo.setSeq(seq);
        sb01Dao.clickSB01Vo(sb01Vo);
        sb01Vo.setSessionid(userid);
        SB01Vo sb01voResult = sb01Dao.selectOneSB01Vo(sb01Vo);
        if (!ObjectUtils.isEmpty(sb01voResult)) {
            if (userid.equals(sb01voResult.getUserid())) {
                sb01voResult.setDeleteYn(true);
            }
        }
        sb01voResult.setShowDate(DateUtil.getDateFormat(sb01voResult.getRegdate()));
        return SB01Composer.entityToResponse(sb01voResult);
    }

    public int updateSB01Vo(SB01Vo sb01Vo, MultipartHttpServletRequest request) throws Exception {

        MultipartFile video = null;
        Iterator<String> itr = request.getFileNames();

        if (!ObjectUtils.isEmpty(itr)) {
            while (itr.hasNext()) {
                video = request.getFile(itr.next());
            }
            String timestamp = DateUtil.getTodayTime();
            StringBuilder sb = new StringBuilder("video/" + timestamp);
            sb01Vo.setRegdate(timestamp);

            if (CommonUtil.chkVideoFile(video.getOriginalFilename())) {
                sb01Vo.setVideobool(YES_NO.YES);
                sb.append(".mp4");
            } else if (CommonUtil.chkAudioFile(video.getOriginalFilename())) {
                sb01Vo.setVideobool(YES_NO.NO);
                sb.append(".mp3");
            } else {
                throw new AppException(ExceptionMsg.EXT_MSG_INPUT_5);
            }

            String path = s3Properties.getTempPath();
            File file = new File(path + "/" + sb.toString());
            video.transferTo(file);
            String deletedPath = sb01Dao.selectVideo(sb01Vo);
            s3Util.deleteS3File(deletedPath); // S3 파일삭제

            SB01Vo sb01Vo2 = new SB01Vo();
            sb01Vo2.setSeq(sb01Vo.getSeq());
            sb01Vo2.setRegdate(DateUtil.getToday());
            sb01Vo2.setVideopath(sb.toString());
            file.delete();

            sb01Dao.updateVideo(sb01Vo2);
        }

        return sb01Dao.updateSB01Vo(sb01Vo);
    }

    @Transactional(rollbackFor = {Exception.class})
    public SB01Response likeSB01Vo(int seq, String sessionid) throws Exception {
        SB01Vo sb01Vo = new SB01Vo();
        sb01Vo.setSeq(seq);
        sb01Dao.likeSB01Vo(sb01Vo);

        sb01Vo.setSessionid(sessionid);
        sb01Vo.setDatelog(DateUtil.getTodayTime());

        sb01Dao.likelogSB01Vo(sb01Vo);

        sb01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
        return SB01Composer.entityToResponse(sb01Vo);
    }

    @Transactional(rollbackFor = {Exception.class})
    public SB01Response hateSB01Vo(int seq, String sessionid) throws Exception {
        SB01Vo sb01Vo = new SB01Vo();
        sb01Vo.setSeq(seq);
        sb01Dao.hateSB01Vo(sb01Vo);

        sb01Vo.setSessionid(sessionid);
        sb01Vo.setDatelog(DateUtil.getTodayTime());

        sb01Dao.hatelogSB01Vo(sb01Vo);

        sb01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
        return SB01Composer.entityToResponse(sb01Vo);
    }

    @Transactional(rollbackFor = {Exception.class})
    public int deleteSB01Vo(int seq) throws Exception {

        SB01Vo sb01Vo = new SB01Vo();
        sb01Vo.setSeq(seq);
        SB02Vo sb02Vo = new SB02Vo();
        sb02Vo.setSeq01(sb01Vo.getSeq());

        sb02Dao.delete_seqSB02Vo(sb02Vo);

        return sb01Dao.deleteSB01Vo(sb01Vo);
    }

    public InputStream selectVideo(int seq, HttpServletRequest request) throws Exception {

        SB01Vo sb01Vo = new SB01Vo();
        sb01Vo.setSeq(seq);
        return s3Util.getS3FileStream(sb01Dao.selectVideo(sb01Vo));
    }

}
