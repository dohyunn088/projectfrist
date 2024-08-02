package com.green.greenGotell.service;

import java.util.List;

import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.CategoryDTO;
import com.green.greenGotell.domain.dto.ItemDTO;

public interface CategoryService {
	Object getAll();

	Long save(String name, Long parentId);

	void delete(Long id);

	List<CategoryDTO> findByParentId(Long parentId);

	void createProduct(ItemDTO dto);

	void list(Model model, Long id);

}
