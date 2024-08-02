package com.green.greenGotell.service;

import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.ItemDTO;
import com.green.greenGotell.domain.dto.ItemSearchDTO;

public interface LiveService {

	void createItem(ItemDTO dto);

	void list(int page, Model model);

	void showSearchItemList(int page, ItemSearchDTO dto, Model model);

}
