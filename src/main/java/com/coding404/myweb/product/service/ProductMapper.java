package com.coding404.myweb.product.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Mapper //Mapper어노테이션은 인터페이스에...
public interface ProductMapper {

	public int productRegist(ProductVO vo); // 상품등록
	public void productFileRegist(ProductUploadVO vo);//파일등록
	//public ArrayList<ProductVO> getList(String writer); //페이지 생성 전 list불러오기
	//mybatis의 매개변수는 단일값만 가능 두개 이상은 어노테이션으로 명칭을 붙여주어야함
 	public ArrayList<ProductVO> getList(@Param("writer") String writer, @Param("cri") Criteria cri); // 페이지 생성하해서 list불러오기
 	public int getTotal(@Param("writer") String writer, @Param("cri") Criteria cri); //데이터 총 개수 구하기
 	
 	public ProductVO getDetail(int prod_id); //상세
	public int productUpdate(ProductVO vo); //업데이트
	public void productDelete(int prod_id); //삭제
	
	//카테고리 처리
	public ArrayList<CategoryVO> getCategory();
	public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo);
	
	//이미지데이터 조회
	public ArrayList<ProductUploadVO> getAjaxImg(Integer prod_id);
}
