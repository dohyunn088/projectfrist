package com.green.greenGotell.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.CategoryDTO;
import com.green.greenGotell.domain.dto.ItemDTO;
import com.green.greenGotell.domain.entity.CategoryEntity;
import com.green.greenGotell.domain.repository.CategoryRepository;
import com.green.greenGotell.ex.CategoryNotFoundException;
import com.green.greenGotell.service.CategoryService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceProcess implements CategoryService {

	private final CategoryRepository repository;
	private Map<Long, CategoryDTO> categoryCache;
	private Map<Integer, List<CategoryDTO>> categoryLevelCache;

	@Override
	@Transactional(readOnly = true)
	public List<CategoryDTO> getAll() {
		log.info("Fetching all root categories");
		List<CategoryEntity> rootCategories = repository.findByParentIsNull();
		return rootCategories.stream().map(CategoryEntity::toCategoryDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Long save(String name, Long parentId) {
		log.info("Saving category with name: {}", name);
		CategoryEntity parent = null;
		int level = 0;

		if (parentId != null) {
			parent = repository.findById(parentId)
					.orElseThrow(() -> new CategoryNotFoundException("Parent category not found"));
			level = parent.getLevel() + 1;
		}

		CategoryEntity category = CategoryEntity.builder().name(name).parent(parent).level(level).build();

		CategoryEntity savedCategory = repository.save(category);
		return savedCategory.getId();
	}

	@Override
	public List<CategoryDTO> findByParentId(Long parentId) {
		log.info("Fetching categories with parentId: {} and level: {}", parentId);
		List<CategoryEntity> categories = repository.findByParent_id(parentId);
		return categories.stream().map(CategoryEntity::toCategoryDTO).collect(Collectors.toList());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}


	@Override
	public void list(Model model, Long id) {
		// TODO Auto-generated method stub

	}

	@PostConstruct
	@Cacheable("categories")
	public void initializeCache() {
		List<CategoryEntity> allCategories = repository.findAll();
		categoryCache = new ConcurrentHashMap<>();
		categoryLevelCache = new ConcurrentHashMap<>();

		for (CategoryEntity category : allCategories) {
			CategoryDTO dto = category.toCategoryDTO();
			categoryCache.put(dto.getId(), dto);

			categoryLevelCache.computeIfAbsent(dto.getLevel(), k -> new ArrayList<>()).add(dto);
		}
	}

	@Override
	public void createProduct(ItemDTO dto) {
		// TODO Auto-generated method stub
		
	}
}
