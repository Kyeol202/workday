package com.bno.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bno.dto.BoardPager;
import com.bno.dto.JoinDto;
import com.bno.dto.SearchDto;
import com.bno.dto.UserInfo;
import com.bno.dto.WorkRecord;
import com.bno.service.WorkRecordService;

@Controller
public class WorkRecordController {
	
	private static Logger logger = LoggerFactory.getLogger(WorkRecordController.class);
	
	@Autowired
	private WorkRecordService service;
	
	
	
	//출근리스트화면
	@RequestMapping(value = "user/userWorkList")
	public String userWorkList() {
		
		
		
		return"work/userWorkList";
	}
	
	//사용자 출근
	@RequestMapping(value = "user/userWorkIn")
	public String userWorkIn(WorkRecord dto) {
		
		service.userWorkIn(dto);
		
		return "work/userWorkList";
	}
	
	//근태관리 리스트
	@RequestMapping(value = "user/userworkListAjax")
	public String userworkListAjax(@RequestParam(value = "cPage", defaultValue = "1")int cPage,
			@RequestParam(value = "searchSort", defaultValue = "")String searchSort,
			@RequestParam(value ="searchVal", defaultValue = "") String searchVal,
			Model model, HttpSession session) {
		logger.info("this is a userWorkListAjax method");
		
		UserInfo user = (UserInfo) session.getAttribute("loginUser");
		model.addAttribute("user", user);
		
		//검색 객체 값 넣기
		SearchDto searchDto = new SearchDto(searchSort, searchVal);
		
		//총 레코드 가져오기
		int nCount = service.selectUserCount(searchDto);
		
		//현재 출력 페이지
		int curPage = cPage;
		
		//페이지 객체에 값 저장
		BoardPager boardPager = new BoardPager(nCount, curPage);
		
		//페이지 겍체에 검색 정보 저장
		boardPager.setSearchSort(searchSort);
		boardPager.setSearchVal(searchVal);
		
		//전체 리스트 출력
		List<JoinDto> workAllList = service.selectUserAllList(boardPager);
		model.addAttribute("workAllList", workAllList);
		model.addAttribute("boardPager", boardPager);
		
		
		
		return "work/ajax/userWorkList_ajax";
	}
	
		//사용자 근무 상세보기
		@RequestMapping(value = "user/WorkRecordSelectOne")
		public String WorkRecordSelectOne(int w_id, Model model, HttpSession session) {
			
			UserInfo user = (UserInfo) session.getAttribute("loginUser");
			model.addAttribute(user);
			
			
			
			return "work/userWorkRecordSelectOne";
		}
	
	
	
}//class end
