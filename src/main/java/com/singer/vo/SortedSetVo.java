package com.singer.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SortedSetVo extends SuperVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4669092346429172855L;
	private String key;
	private double score;

}
