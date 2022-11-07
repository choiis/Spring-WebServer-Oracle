package com.singer.domain.entity;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.singer.common.util.Constants.USER_CODE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class CommVo extends SuperVo {

    private static final long serialVersionUID = -8496452258072990844L;

    private int seq;

    private String codecd;

    private String codenm;

    private String codegrp;

    private String username;

    private String regdate;

    private String reguser;

    private String menucd;

    private String menunm;

    private String menuurl;

    private USER_CODE authlevel;

    private String moduser;

    private String moddate;

    private String codegrpnm;

    private String password;

    private HttpStatus errorCode;

    private String errorMsg;

    private List<CommVo> commList;

    private String userId;

    private String tableName;

}