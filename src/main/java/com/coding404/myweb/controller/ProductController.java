package com.coding404.myweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.product.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	@Qualifier("productService")
	private ProductService productService;

	@GetMapping("/productList")
	public String list(Model model) {
		
		//로그인 기능이 없으므로 admin 이라는 계정기반으로 조회
		String writer = "admin";
		ArrayList<ProductVO> list = productService.getList(writer);
		model.addAttribute("list", list);
		return "product/productList";
	}
	@GetMapping("/productReg")
	public String reg() {
		return "product/productReg";
	}
	@GetMapping("/productDetail")
	public String detail(@RequestParam("prod_id") int prod_id, Model model) {
		//조회 ~ prod_id가 필요
		//System.out.println(prod_id);
		
		ProductVO vo = productService.getDetail(prod_id);
		model.addAttribute("vo", vo);
		
		return "product/productDetail";
	}
	
	//Post방식
	//등록요청
	@PostMapping("/registForm")
	public String registForm(ProductVO vo, RedirectAttributes ra) {
		
		int result = productService.productRegist(vo);
		String msg = result == 1 ? "등록되었습니다" : "등록실패";
		ra.addFlashAttribute("msg", msg);
		
		
		return "redirect:/product/productList";
	}
	
	//post요청
	//수정요청
	@PostMapping("/modifyForm")
	public String modifyForm(ProductVO vo, RedirectAttributes ra ){
		System.out.println(vo.toString());
		//메서드명 = productUpdate()
		//데이터베이스 업데이트 진행
		//업데이트된 결과를 반환받아서 list화면으로 "업데이트 성공" 메세지를 띄우기
		
			int result = productService.productUpdate(vo);
			String msg2 = result == 1 ? "업데이트 성공" : "업데이트 실패";
			ra.addFlashAttribute("msg2", msg2);
		
		return "redirect:/product/productList";
	}
	
	//post요청
	//삭제요청
	@PostMapping("deleteForm")
	public String deleteForm(@RequestParam("prod_id") int prod_id) {
		productService.productDelete(prod_id);
		
		return "redirect:/product/productList";
	}
}
