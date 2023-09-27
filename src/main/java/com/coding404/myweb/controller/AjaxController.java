package com.coding404.myweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.product.service.ProductService;

@RestController
public class AjaxController {

	@Autowired
	private ProductService productService;
	
	//업로드경로
	@Value("${project.upload.path}") //application.properties의 값 가져오기
	private String uploadPath;
	
	@GetMapping("/getCategory")
	public ResponseEntity<ArrayList<CategoryVO>> getCategory() {
		
		ArrayList<CategoryVO> list = productService.getCategory();
		
		//return new ResponseEntity<>(productService.getCategory(), HttpStatus.OK);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/getCategoryChild/{a}/{b}/{c}")
	public ResponseEntity<ArrayList<CategoryVO>> getCategoryChild(@PathVariable("a") String group_id,
																  @PathVariable("b") Integer category_lv,
																  @PathVariable("c") Integer category_detail_lv) {
		
//		System.out.println(group_id);
		System.out.println("카테고리lv: " + category_lv);
//		System.out.println(category_detail_lv);
		CategoryVO vo = CategoryVO.builder()
								  .group_id(group_id)
								  .category_lv(category_lv)
								  .category_detail_lv(category_detail_lv)
								  .build();
		
		ArrayList<CategoryVO> list = productService.getCategoryChild(vo);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	//////////////////////////////////////////////////////////////////////////
	//이미지 데이터 화면에 응답하기
	//1. 화면에서 넘어오는 getAjaxIng요청을 받는 REST API 를 생성
	//1-1. 서비스, 매퍼 함수를 생성
	//2. getAjaxImg()로 업로드 테이블에서 조회한 결과를 화면으로 전달.
	
	@PostMapping("/getAjaxImg")
	public ResponseEntity<ArrayList<ProductUploadVO>> getAjaxImg(@RequestBody ProductUploadVO vo) {
		
		ArrayList<ProductUploadVO> list = productService.getAjaxImg(vo.getProd_id());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	//이미지 src값 응답하기
	@GetMapping("/display")
	public ResponseEntity<byte[]> display(@RequestParam("filename") String filename,
										  @RequestParam("filepath") String filepath,
										  @RequestParam("uuid") String uuid) {
		
		String path = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
		File file = new File(path);
		
		byte[]arr = null;
		try {
			arr = FileCopyUtils.copyToByteArray(file); //파일경로 기반으로 데이터를 구함
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(arr, HttpStatus.OK);
		
	}
	
	
	
	
}
