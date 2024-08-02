package com.green.greenGotell.service;

import java.util.List;

import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.ItemDTO;
import com.green.greenGotell.domain.dto.ItemSearchDTO;

public interface ItemService {

	void createItem(ItemDTO dto);

	List<ItemDTO> list(int page, Model model);

	void showSearchItemList(int page, ItemSearchDTO dto, Model model);


}
