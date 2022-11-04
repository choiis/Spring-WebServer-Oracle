package com.singer.domain.vo;

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
@EqualsAndHashCode(callSuper = true)
public class SortedSetVo extends SuperVo {

	private static final long serialVersionUID = 7483045704969139063L;
	private String key;
	private double score;

}
