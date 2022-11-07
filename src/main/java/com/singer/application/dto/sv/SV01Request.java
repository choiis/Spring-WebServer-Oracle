package com.singer.application.dto.sv;

import com.singer.common.exception.ExceptionMsg;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SV01Request {

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_1)
    private String title;

    @NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_2)
    private String text;

    private String userid;


    private String regdate;

    private int multiselect;

    private List<SV02Request> list;

}
