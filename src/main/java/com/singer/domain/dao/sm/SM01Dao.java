package com.singer.domain.dao.sm;

import com.singer.domain.dao.SuperDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sm.SM01Entity;

@Repository("sm01Dao")
public class SM01Dao extends SuperDao {

    private static final String namespace = "com.singer.mappers.SM01";

    public int insertSM01Vo(SM01Entity sm01Vo) throws Exception {
        return insert(namespace + ".insert", sm01Vo);
    }

    public int insertSMI1Vo(SM01Entity sm01Vo) throws Exception {
        return update(namespace + ".insertSMI1", sm01Vo);
    }

    public int insertSME1Vo(SM01Entity sm01Vo) throws Exception {
        return insert(namespace + ".insertSME1", sm01Vo);
    }

    public int updateSM01Vo(SM01Entity sm01Vo) throws Exception {
        return update(namespace + ".update", sm01Vo);
    }

    public int updateUserType(SM01Entity sm01Vo) throws Exception {
        return update(namespace + ".updateUserType", sm01Vo);
    }

    @SuppressWarnings("unchecked")
    public List<SM01Entity> selectSM01Vo(SM01Entity sm01Vo) throws Exception {
        return (List<SM01Entity>) selectList(namespace + ".select", sm01Vo);
    }

    @SuppressWarnings("unchecked")
    public List<SM01Entity> selectSMI1Vo(SM01Entity sm01Vo) throws Exception {
        return (List<SM01Entity>) selectList(namespace + ".selectSMI1", sm01Vo);
    }

    public SM01Entity selectOneSM01Vo(SM01Entity sm01Vo) throws Exception {
        return (SM01Entity) selectOne(namespace + ".selectOne", sm01Vo);
    }

    public SM01Entity selectLoginSM01Vo(SM01Entity sm01Vo) throws Exception {
        return (SM01Entity) selectOne(namespace + ".selectLogin", sm01Vo);
    }

    public int deleteSM01Vo(SM01Entity sm01Vo) throws Exception {
        return delete(namespace + ".delete", sm01Vo);
    }

    public int deleteSME1Vo(SM01Entity sm01Vo) throws Exception {
        return delete(namespace + ".deleteSME1", sm01Vo);
    }

    public int insertImage(Map<String, Object> hashMap) throws Exception {
        return insert(namespace + ".insertImage", hashMap);
    }

    public int updateImage(Map<String, Object> hashMap) throws Exception {
        return insert(namespace + ".updateImage", hashMap);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Object> selectImage(SM01Entity sm01Vo) throws Exception {
        return (HashMap<String, Object>) selectOne(namespace + ".selectImage", sm01Vo);
    }

}
