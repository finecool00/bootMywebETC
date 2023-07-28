package com.coding404.myweb.command;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVO {
	private Integer prod_id;
	private LocalDateTime prod_regdate;
	private String prod_enddate;
	private String prod_category;
	private String prod_writer;
	//@NotBlank
	private String prod_name;
	//@Pattern(regexp = "[0-9]{3,}", message="숫자를 입력하세요")
	private Integer prod_price;
	//@Pattern(regexp = "[0-9]{1,}", message="1이상의 숫자를 입력하세요")
	private Integer prod_count;
	//@Pattern(regexp = "[0-9]{1,3}", message="0이상 100이하의 숫자를 입력하세요")
	private Integer prod_discount;
	private String prod_purchase_yn;
	//@NotBlank
	private String prod_content;
	//@NotBlank
	private String prod_comment;
}
