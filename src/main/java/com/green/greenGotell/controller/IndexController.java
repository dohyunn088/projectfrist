package com.green.greenGotell.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.AttendanceListDTO;
import com.green.greenGotell.domain.dto.ItemDTO;
import com.green.greenGotell.domain.dto.NoticeListDTO;
import com.green.greenGotell.security.CustomUserDetails;
import com.green.greenGotell.service.IndexService;
import com.green.greenGotell.service.ItemService;
import com.green.greenGotell.service.NoticeService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@RequiredArgsConstructor
@Controller
public class IndexController {
	
	private final IndexService indexService;
	private final NoticeService noticeService;
	private final ItemService itemService;
	
	
	  @GetMapping("/") public String getMethodName(@AuthenticationPrincipal
	  CustomUserDetails userDetails,Model model) {
	  
	  //프로필
	  indexService.list(userDetails,model);
	  
	  // 최근 공지사항 목록을 모델에 추가
      List<NoticeListDTO> recentNotices = noticeService.getRecentNotices();
      model.addAttribute("recentNotices", recentNotices);
      
      List<ItemDTO> itemList = itemService.list(0, model);
      
      
	  return "index"; }
	  
	  
	  
	 
	  
	//출근버튼 클릭
		
	@PostMapping("/clock-in")
	@ResponseBody
	public void postMethodName(@AuthenticationPrincipal CustomUserDetails userDetails) {
		
	    indexService.clockIn(userDetails);

		
	}
	
	
	//퇴근버튼 클릭
	@PutMapping("/clock-out")
	@ResponseBody
    public void clockOut(@AuthenticationPrincipal CustomUserDetails userDetails) {
        
        indexService.clockOut(userDetails);
	}
	
	
    @GetMapping("/status")
    @ResponseBody
	 public AttendanceListDTO getAttendanceStatus(@AuthenticationPrincipal CustomUserDetails userDetails) {
	    
	        return indexService.AttendanceStatus(userDetails);
	  }

	
	
	

}
