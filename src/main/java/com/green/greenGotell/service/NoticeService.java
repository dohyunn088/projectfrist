package com.green.greenGotell.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import com.green.greenGotell.domain.dto.NoticeListDTO;
import com.green.greenGotell.domain.dto.NoticeSaveDTO;
import com.green.greenGotell.domain.dto.NoticeUpdateDTO;
import com.green.greenGotell.domain.entity.NoticeEntity;
import com.green.greenGotell.security.CustomUserDetails;

public interface NoticeService {

	void listProcess(int division, int page, Model model);

	void saveProcess(Long id, NoticeSaveDTO dto);
	void saveProcess(CustomUserDetails userDetails, NoticeSaveDTO dto);

	void detailProcess(CustomUserDetails userDetails, long no, Model model);

	void updateProcess(long no, NoticeUpdateDTO dto);

	void deleteProcess(long no);

	void listProcessByUser(Long userId, int page, Model model);

	Page<NoticeEntity> searchNotices(String division, String searchType, String keyword, Pageable pageable);

	List<NoticeListDTO> getRecentNotices();

}
