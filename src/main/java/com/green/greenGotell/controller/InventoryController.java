package com.green.greenGotell.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.greenGotell.domain.dto.CategoryDTO;
import com.green.greenGotell.domain.dto.ItemDTO;
import com.green.greenGotell.domain.dto.ItemSearchDTO;
import com.green.greenGotell.domain.entity.ItemEntity;
import com.green.greenGotell.service.CategoryService;
import com.green.greenGotell.service.InventoryService;
import com.green.greenGotell.service.ItemService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
	private final CategoryService categoryService;
	private final ItemService itemservice; 
	
	// 초기 카테고리 조회
	@GetMapping
	public String listCategories(@RequestParam(name="page", defaultValue = "0") int page, ItemSearchDTO dto, Model model) {
		if (dto == null) {
			dto = new ItemSearchDTO(); // dto가 null일 경우 새 객체로 초기화
		}
		itemservice.list(page, model);
		model.addAttribute("categories", categoryService.getAll());
		itemservice.showSearchItemList(page, dto, model);
		return "views/inventory/item-list";
	}
    
    /*
	//선택 카테고리 조회
	@GetMapping("/search")
	public String list(@RequestParam(name="page",defaultValue = "0") int page,ItemSearchDTO dto, Model model) {
		//System.out.println("max>>>>"+dto.maxCategoryId());
		itemservice.showSearchItemList(page,dto,model);
		return "views/inventory/item-list";
	}*/
	
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
        return "views/inventory/item-write";
    }
    
    @PostMapping("/create")
    public String createCategory(ItemDTO dto) {
        itemservice.createItem(dto);
        return "redirect:/inventory";
    }
    

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "redirect:/inventory";
    }
}