package com.singer.domain.entity;

import java.io.Serializable;

import com.singer.common.util.Constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuperEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = -594197827136251262L;
    protected boolean deleteYn;
    protected String showDate;
    protected int totCnt;

    protected int startRownum;
    protected int endRownum;

    @Setter(AccessLevel.NONE)
    protected int nowPage;
    protected int rowPerPage;
    protected int result;
    protected int like;

    protected SuperEntity() {
        this.rowPerPage = Constants.ROW_PER_PAGE;
    }

    public final void setNowPage(int nowPage) {
        this.nowPage = nowPage;
        this.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE + 1);
        this.setEndRownum(nowPage * Constants.ROW_PER_PAGE);
    }

}
