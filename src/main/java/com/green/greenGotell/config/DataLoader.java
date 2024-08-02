package com.green.greenGotell.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.green.greenGotell.service.CategoryService;

//@Configuration
public class DataLoader {
	@Bean
    CommandLineRunner initDatabase(CategoryService categoryService) {
        return args -> {
            // 대분류: 의류
            Long clothingId = categoryService.save("의류", null);

            // 중분류: 남성 의류
            Long mensClothingId = categoryService.save("남성 의류", clothingId);
            categoryService.save("정장", mensClothingId);
            categoryService.save("캐주얼", mensClothingId);
            categoryService.save("스포츠웨어", mensClothingId);
            categoryService.save("수영복", mensClothingId);

            // 중분류: 여성 의류
            Long womensClothingId = categoryService.save("여성 의류", clothingId);
            categoryService.save("드레스", womensClothingId);
            categoryService.save("캐주얼", womensClothingId);
            categoryService.save("스포츠웨어", womensClothingId);
            categoryService.save("수영복", womensClothingId);

            // 중분류: 아동 의류
            Long kidsClothingId = categoryService.save("아동 의류", clothingId);
            categoryService.save("정장", kidsClothingId);
            categoryService.save("캐주얼", kidsClothingId);
            categoryService.save("스포츠웨어", kidsClothingId);
            categoryService.save("수영복", kidsClothingId);
        };
    }
}
