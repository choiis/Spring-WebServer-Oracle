package com.singer.application.service.sm;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.common.util.CommonUtil;
import com.singer.common.util.Constants;
import com.singer.common.util.Constants.PHONE_INFO_CODE;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sm.SM01Dao;
import com.singer.domain.entity.sm.SM01Vo;

import oracle.sql.BLOB;

@Service
public class SM01ServiceImpl implements SM01Service {

    @Autowired
    private SM01Dao sm01Dao;

    // @Autowired
    // @Qualifier("aes256")
    // private AES256Util aes256Util;

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public HashMap<String, Object> insertSM01Vo(SM01Vo sm01Vo, MultipartHttpServletRequest request) throws Exception {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();

        // String pw = aes256Util.aesEncode(sm01Vo.getPasswd());
        // sm01Vo.setPasswd(pw);

        sm01Vo.setRegdate(DateUtil.getToday());
        sm01Vo.setGrade(Constants.USER_CODE.NORMAL);

        MultipartFile photo = null;
        Iterator<String> itr = request.getFileNames();
        if (ObjectUtils.isEmpty(itr)) {
            throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
        }
        while (itr.hasNext()) {
            photo = request.getFile(itr.next());
        }

        if (!CommonUtil.chkIMGFile(photo.getOriginalFilename())) {
            throw new AppException(ExceptionMsg.EXT_MSG_INPUT_4);
        }

        hashMap.put("succeed", sm01Dao.insertSM01Vo(sm01Vo));
        sm01Vo.setInfocode(PHONE_INFO_CODE.CELL);
        sm01Dao.insertSMI1Vo(sm01Vo);
        HashMap<String, Object> putHash = new HashMap<String, Object>();
        putHash.put("userid", sm01Vo.getUserid());
        putHash.put("regdate", DateUtil.getToday());
        putHash.put("photo", photo.getBytes());

        sm01Dao.insertImage(putHash);

        return putHash;
    }

    @Override
    public List<SM01Vo> selectSM01Vo(SM01Vo sm01Vo) throws Exception {

        int nowPage = sm01Vo.getNowPage();
        sm01Vo.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE);
        sm01Vo.setEndRownum(nowPage * Constants.ROW_PER_PAGE);

        return sm01Dao.selectSM01Vo(sm01Vo);
    }

    @Override
    public SM01Vo selectOneSM01Vo(SM01Vo sm01Vo) throws Exception {
        sm01Vo = sm01Dao.selectOneSM01Vo(sm01Vo);
        List<SM01Vo> list = sm01Dao.selectSMI1Vo(sm01Vo);
        for (SM01Vo vo : list) {

            if (vo.getInfocode() == PHONE_INFO_CODE.CELL) {
                sm01Vo.setCellpfnum(vo.getPfnum());
                sm01Vo.setCellpcnum(vo.getPcnum());
                sm01Vo.setCellpbnum(vo.getPbnum());
            } else if (vo.getInfocode() == PHONE_INFO_CODE.HOME) {
                sm01Vo.setHomepfnum(vo.getPfnum());
                sm01Vo.setHomepcnum(vo.getPcnum());
                sm01Vo.setHomepbnum(vo.getPbnum());
            } else if (vo.getInfocode() == PHONE_INFO_CODE.COMPANY) {
                sm01Vo.setCompanypfnum(vo.getPfnum());
                sm01Vo.setCompanypcnum(vo.getPcnum());
                sm01Vo.setCompanypbnum(vo.getPbnum());
            } else if (vo.getInfocode() == PHONE_INFO_CODE.OTHER) {
                sm01Vo.setOtherpfnum(vo.getPfnum());
                sm01Vo.setOtherpcnum(vo.getPcnum());
                sm01Vo.setOtherpbnum(vo.getPbnum());
            }
        }
        return sm01Vo;
    }

    @Override
    public SM01Vo login(SM01Vo sm01Vo) throws Exception {
        return sm01Dao.selectLoginSM01Vo(sm01Vo);
    }

    @Override
    public int deleteSM01Vo(SM01Vo sm01Vo) throws Exception {
        return sm01Dao.deleteSM01Vo(sm01Vo);
    }

    @Override
    public InputStream selectImage(SM01Vo sm01Vo, HttpServletRequest request) throws Exception {

        InputStream is = null;

        HashMap<String, Object> hashMap = sm01Dao.selectImage(sm01Vo);
        if (CollectionUtils.isEmpty(hashMap)) {
            throw new ClientException(HttpStatus.NOT_FOUND);
        } else { // 이미지 불러오기 성공시
            BLOB images = (BLOB) hashMap.get("PHOTO");

            is = images.getBinaryStream();

        }

        return is;
    }

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public SM01Vo updateSM01Vo(SM01Vo sm01Vo, MultipartHttpServletRequest request, String userId) throws Exception {

        MultipartFile photo = null;
        Iterator<String> itr = request.getFileNames();

        while (itr.hasNext()) {
            photo = request.getFile(itr.next());
        }
        // 저장후
        sm01Dao.updateSM01Vo(sm01Vo);
        // 전화번호 변경
        if (!StringUtils.isEmpty(sm01Vo.getCellpcnum()) && !StringUtils.isEmpty(sm01Vo.getCellpbnum())) {
            SM01Vo vo = new SM01Vo();
            vo.setUserid(sm01Vo.getUserid());
            vo.setInfocode(PHONE_INFO_CODE.CELL);
            vo.setPfnum(sm01Vo.getCellpfnum());
            vo.setPcnum(sm01Vo.getCellpcnum());
            vo.setPbnum(sm01Vo.getCellpbnum());
            vo.setRegdate(DateUtil.getToday());
            sm01Dao.insertSMI1Vo(vo);
        }

        if (!StringUtils.isEmpty(sm01Vo.getHomepcnum()) && !StringUtils.isEmpty(sm01Vo.getHomepbnum())) {
            SM01Vo vo = new SM01Vo();
            vo.setUserid(sm01Vo.getUserid());
            vo.setInfocode(PHONE_INFO_CODE.HOME);
            vo.setPfnum(sm01Vo.getHomepfnum());
            vo.setPcnum(sm01Vo.getHomepcnum());
            vo.setPbnum(sm01Vo.getHomepbnum());
            vo.setRegdate(DateUtil.getToday());
            sm01Dao.insertSMI1Vo(vo);
        }

        if (!StringUtils.isEmpty(sm01Vo.getCompanypcnum()) && !StringUtils.isEmpty(sm01Vo.getCompanypbnum())) {
            SM01Vo vo = new SM01Vo();
            vo.setUserid(sm01Vo.getUserid());
            vo.setInfocode(PHONE_INFO_CODE.COMPANY);
            vo.setPfnum(sm01Vo.getCompanypfnum());
            vo.setPcnum(sm01Vo.getCompanypcnum());
            vo.setPbnum(sm01Vo.getCompanypbnum());
            vo.setRegdate(DateUtil.getToday());
            sm01Dao.insertSMI1Vo(vo);
        }

        if (!StringUtils.isEmpty(sm01Vo.getOtherpcnum()) && !StringUtils.isEmpty(sm01Vo.getOtherpbnum())) {
            SM01Vo vo = new SM01Vo();
            vo.setUserid(sm01Vo.getUserid());
            vo.setInfocode(PHONE_INFO_CODE.OTHER);
            vo.setPfnum(sm01Vo.getOtherpfnum());
            vo.setPcnum(sm01Vo.getOtherpcnum());
            vo.setPbnum(sm01Vo.getOtherpbnum());
            vo.setRegdate(DateUtil.getToday());
            sm01Dao.insertSMI1Vo(vo);
        }

        if (photo.getSize() != 0) {

            if (!CommonUtil.chkIMGFile(photo.getOriginalFilename())) {
                throw new AppException(ExceptionMsg.EXT_MSG_INPUT_4);
            }
            HashMap<String, Object> putHash = new HashMap<String, Object>();
            putHash.put("userid", sm01Vo.getUserid());
            putHash.put("regdate", DateUtil.getToday());
            putHash.put("photo", photo.getBytes());

            sm01Dao.updateImage(putHash);
        }
        sm01Vo.setUserid(userId);
        // 재 조회
        return sm01Dao.selectOneSM01Vo(sm01Vo);
    }

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public int updateSME1Vo(SM01Vo sm01Vo, String userId) throws Exception {
        sm01Vo.setInsertid(userId);
        sm01Dao.updateUserType(sm01Vo);
        if (sm01Vo.getUsertype() == Constants.USER_CODE.ADMIN) {
            sm01Dao.insertSME1Vo(sm01Vo);
        } else {
            sm01Dao.deleteSME1Vo(sm01Vo);
        }
        return 0;
    }

}
