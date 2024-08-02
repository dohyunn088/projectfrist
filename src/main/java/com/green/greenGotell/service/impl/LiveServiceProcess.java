package com.green.greenGotell.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.ItemDTO;
import com.green.greenGotell.domain.dto.ItemSearchDTO;
import com.green.greenGotell.domain.entity.CategoryEntity;
import com.green.greenGotell.domain.entity.ItemEntity;
import com.green.greenGotell.domain.repository.CategoryRepository;
import com.green.greenGotell.domain.repository.ItemRepository;
import com.green.greenGotell.domain.repository.LiveRepository;
import com.green.greenGotell.service.ItemService;
import com.green.greenGotell.service.LiveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiveServiceProcess implements LiveService {
	private final LiveRepository liveRepository;	
	private final CategoryRepository categoryRepository;

	@Override
	public void createItem(ItemDTO dto) {

		// 저장된 ItemEntity를 ItemDTO로 변환하여 반환
		liveRepository.save(dto.toEntity());

	}

	
	@Override
	public void list(int page, Model model) {
		
		Sort sort = Sort.by(Direction.DESC,"id");
		
		Pageable pageable = PageRequest.of(page,10, sort);
		
		Page<ItemEntity> item = liveRepository.findAll(pageable);
		
		model.addAttribute("inventorys", item.getContent().stream().map(ItemEntity :: toItemDTO).collect(Collectors.toList()));
		model.addAttribute("currentPage", item.getNumber());
		model.addAttribute("totalPages", item.getTotalPages());
	}


	@Override
	public void showSearchItemList(int page, ItemSearchDTO dto, Model model) {
		
		Sort sort = Sort.by(Direction.DESC,"id");
		Pageable pageable = PageRequest.of(page,10, sort);
		
		Page<ItemEntity> itemEntity;
		if (dto.isEmpty()) {
			itemEntity = liveRepository.findAll(pageable);
		}else {
			itemEntity = liveRepository.findBySearchItem(
					dto.getName(),
					dto.maxCategoryId(),
					pageable);
		}
		model.addAttribute("inventorys", itemEntity.getContent().stream().map(ItemEntity :: toItemDTO).collect(Collectors.toList()));
		
		model.addAttribute("currentPage", itemEntity.getNumber());
		model.addAttribute("totalPages", itemEntity.getTotalPages());
	}

}
