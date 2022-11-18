package com.singer.application.dto.sm;

import javax.validation.constraints.NotEmpty;

import com.singer.common.exception.ExceptionMsg;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SM02Request {

	@NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_1)
	String title;

	@NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_2)
	String text;

}
