package com.green.greenGotell.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.greenGotell.service.AttendanceService;
import com.green.greenGotell.service.PersonnelService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AttendanceController {
	
	private final AttendanceService attendanceService;
	
	@GetMapping("/personnel/attendance")
	public String attendanceIist(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		
		
		attendanceService.showAttendanceList(page,model);
		
		return "/views/personnel/attendance/attendanceRecord";
	}
	
	
	@GetMapping("/personnel/attendance/leave-request")
	public String gleaveRequestIist() {
		return "/views/personnel/attendance/leaveRequest";
	}
	

}
