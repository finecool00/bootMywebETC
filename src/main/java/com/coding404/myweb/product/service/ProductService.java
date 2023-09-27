package com.coding404.myweb.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

public interface ProductService {
	
	public int productRegist(ProductVO vo, List<MultipartFile> list);
	//public ArrayList<ProductVO> getList(String writer); //조회

	
	public ArrayList<ProductVO> getList(String writer, Criteria cri); //조회
	public int getTotal(String writer, Criteria cri); //데이터 총 개수 구하기
	
	public ProductVO getDetail(int prod_id); //상세
	public int productUpdate(ProductVO vo); //업데이트
	public void productDelete(int prod_id); //삭제
	
	//카테고리 처리
	public ArrayList<CategoryVO> getCategory();
	public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo);
	
	//이미지 데이터 조회
	public ArrayList<ProductUploadVO> getAjaxImg(Integer prod_id);

}
