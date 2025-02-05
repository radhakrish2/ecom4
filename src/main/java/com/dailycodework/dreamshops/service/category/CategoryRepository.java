package com.dailycodework.dreamshops.service.category;



import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dreamshops.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

    Category findByName(String name);

	boolean existsByName(String name);

}
