package com.coding404.myweb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coding404.myweb.util.KakaoAPI;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private KakaoAPI kakaoAPI;
	
	@GetMapping("/join")
	public String join() {
		return "/user/join";
	}
	@GetMapping("/login")
	public String login() {
		return "/user/login";
	}
	@GetMapping("/userDetail")
	public String detail() {
		return "/user/userDetail";
	}
	
	@GetMapping("/kakao")
	public String kakao(@RequestParam("code") String code) {
		
		//토큰받기
		String token = kakaoAPI.getToken(code);
		//System.out.println(token);
		
		//유저정보받기
		Map<String, Object> map = kakaoAPI.getUser(token);
		
		System.out.println("카카오에서 받은 사용자정보:" + map.toString());
		
		//우리데이터베이스 조회 or 확인 -> 가입으로 연결 or 로그인처리
		//
		
		return "redirect:/main"; //메인화면으로
	}
	
	
}
