package com.singer.domain.entity.sm;

import com.singer.domain.entity.SuperEntity;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.singer.common.util.CommonUtil;
import com.singer.common.util.Constants.PHONE_INFO_CODE;
import com.singer.common.util.Constants.USER_CODE;
import com.singer.common.exception.ExceptionMsg;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SM01Entity extends SuperEntity {

    private static final long serialVersionUID = 5452779898991723254L;

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INF_1)
    private String userid;
    @NotEmpty(message = ExceptionMsg.EXT_MSG_INF_2)
    private String passwd;
    @NotEmpty(message = ExceptionMsg.EXT_MSG_INF_3)
    private String username;

    @Pattern(regexp = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$", message = ExceptionMsg.EXT_MSG_INPUT_12)
    private String brth;
    private USER_CODE grade;
    private String regdate;
    private String phone;

    @Email(message = ExceptionMsg.EXT_MSG_INPUT_13)
    private String email;
    private USER_CODE usertype;
    private MultipartFile photo;

    private String device;
    private String browser;
    private String insertid;

    private List<SM01Entity> list;

    private PHONE_INFO_CODE infocode;
    private String pfnum;
    private String pcnum;
    private String pbnum;

    private String cellpfnum;
    private String cellpcnum;
    private String cellpbnum;

    private String homepfnum;
    private String homepcnum;
    private String homepbnum;

    private String companypfnum;
    private String companypcnum;
    private String companypbnum;

    private String otherpfnum;
    private String otherpcnum;
    private String otherpbnum;

    public void setList(List<SM01Entity> list) {
        this.list = list;
        super.setNowPage(super.getNowPage() + 1);
        // �럹�씠吏뺤쓣 �쐞�븳 移댁슫�듃
        if (list.size() != 0) {
            super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
        } else {
            super.setTotCnt(0);
        }
    }

}