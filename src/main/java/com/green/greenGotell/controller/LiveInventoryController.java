package com.green.greenGotell.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.greenGotell.domain.dto.CategoryDTO;
import com.green.greenGotell.service.CategoryService;
import com.green.greenGotell.service.ItemService;
import com.green.greenGotell.service.LiveService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/liveInventory")
@RequiredArgsConstructor
public class LiveInventoryController {
	private final CategoryService categoryService;
	private final LiveService liveService; 
	@GetMapping
	public String list() {
		return "/views/inventory/live_list";
	}
	
	@GetMapping("/arrival_exit")
	public String createLive() {
		return "/views/inventory/live_write";
	}
	// 중분류
    @GetMapping("/categories")
    @ResponseBody
    public List<CategoryDTO> getCategoriesByParentId(@RequestParam(name = "parentId") Long parentId) {
    	
    	System.out.println(">>>"+parentId);
        return categoryService.findByParentId(parentId); 
    }
    // 소분류
    @GetMapping("/subcategories")
    @ResponseBody
    public List<CategoryDTO> getSubCategoriesByParentId(@RequestParam(name = "parentId") Long parentId) {
        return categoryService.findByParentId(parentId); 
    }

    @GetMapping("/create")
    public String showAddForm(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "views/inventory/live_write";
    }
	

}
