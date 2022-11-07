package com.singer.domain.entity.sr;

import java.io.Serializable;

import org.hibernate.validator.constraints.Range;
import com.singer.common.exception.ExceptionMsg;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SR02Vo implements Serializable, Cloneable {

	private static final long serialVersionUID = -86445236446569673L;

	private int seq;

	protected String userid;
	protected String regdate;

	@Range(min = 0, max = 5, message = ExceptionMsg.EXT_MSG_INPUT_7)
	private double grade;

}
