package com.green.greenGotell.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.greenGotell.domain.dto.NoticeListDTO;
import com.green.greenGotell.domain.dto.NoticeSaveDTO;
import com.green.greenGotell.domain.dto.NoticeUpdateDTO;
import com.green.greenGotell.domain.entity.NoticeEntity;
import com.green.greenGotell.security.CustomUserDetails;
import com.green.greenGotell.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/notices")
@RequiredArgsConstructor
@Controller
public class NoticesController {
	
	private final NoticeService service;
	
	//공지사항 전체 게시글 조회
    @GetMapping
    public String list() {
        return "redirect:/notices/0/1"; // 페이지 번호 추가
    }
    
    // 공지사항 부서별 게시글 조회
    @GetMapping("/{division}/{page}")
    public String list(@PathVariable("division") int division, @PathVariable("page") int page, Model model) {
        service.listProcess(division, page, model);
        return "views/notices/list";
    }

	// 사용자의 게시물만 조회
    @GetMapping("/my/{page}")
    public String listMyNotices(@PathVariable("page") int page, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        service.listProcessByUser(userDetails.getId(), page, model);
        return "views/notices/my_list";
    }
	
	//공지사항 저장하기
	@PostMapping
	public String save(@AuthenticationPrincipal CustomUserDetails userDetails, NoticeSaveDTO dto) {
	    // DTO를 엔티티로 변환하고 저장합니다.
		//service.saveProcess(userDetails, dto);
		service.saveProcess(userDetails.getId() , dto);
		return "redirect:/notices";
	}
	
	//공지사항 만들기
	@GetMapping("/new")
	public String write() {
		return "views/notices/write";
	}

	//상세페이지 조회
	@GetMapping("/detail/{no}")
	public String detail(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("no") long no, Model model) {	
		service.detailProcess(userDetails, no, model);
		return "views/notices/detail";
	}
	
	//상세페이지 수정
	@PutMapping("/{no}")
	public String update(@PathVariable("no")long no, NoticeUpdateDTO dto) {	
		service.updateProcess(no, dto);
		return "redirect:/notices/detail/{no}";
	}

	//해당 공지사항 삭제
	@DeleteMapping("/{no}")
	public String delete(@PathVariable("no") long no) {
		service.deleteProcess(no);
		return "redirect:/notices";
	}

//    @GetMapping("/search")
//    public String search(
//            @RequestParam(name = "division", defaultValue = "1") String division,
//            @RequestParam(name = "searchType", defaultValue = "title") String searchType,
//            @RequestParam(name = "keyword", defaultValue = "") String keyword,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size,
//            Model model
//    ) {
//    	Pageable pageable = PageRequest.of(page, size);
//        Page<NoticeEntity> results = service.searchNotices(division, searchType, keyword, pageable);
//        model.addAttribute("notices", results.getContent());
//        model.addAttribute("page", results);
//        return "views/notices/list"; // 검색 결과를 표시할 뷰 이름
//    }

}

