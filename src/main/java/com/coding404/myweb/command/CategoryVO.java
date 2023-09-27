package com.coding404.myweb.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryVO {
	private Integer category_id;
	private String group_id;
	private Integer category_lv;
	private String category_nm;
	private Integer category_detail_lv;
	private String category_detail_nm;
	private Integer category_parent_lv;
	private Integer category_detail_parent_lv;

}
