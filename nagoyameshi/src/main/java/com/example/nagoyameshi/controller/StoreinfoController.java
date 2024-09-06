package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Storeinfo;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.StoreinfoRepository;

@Controller
@RequestMapping("/storeinfo")
public class StoreinfoController {

    private final StoreinfoRepository storeinfoRepository;
    private final CategoryRepository categoryRepository;

    public StoreinfoController(StoreinfoRepository storeinfoRepository, CategoryRepository categoryRepository) {
        this.storeinfoRepository = storeinfoRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "area", required = false) String area,
                        @RequestParam(name = "category", required = false) Integer categoryId,
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        Model model) {
        
        Page<Storeinfo> storeinfoPage;

        if (keyword != null && !keyword.isEmpty()) {
            storeinfoPage = storeinfoRepository.findByStorenameLikeOrAddressLike("%" + keyword + "%", "%" + keyword + "%", pageable); // Updated method name
        } else if (area != null && !area.isEmpty()) {
            storeinfoPage = storeinfoRepository.findByAddressLike("%" + area + "%", pageable);
        } else if (categoryId != null) {
            storeinfoPage = storeinfoRepository.findByCategoriesId(categoryId, pageable); 
        } else {
            storeinfoPage = storeinfoRepository.findAll(pageable);
        }

        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("storeinfoPage", storeinfoPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("categories", categories);

        return "storeinfo/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Storeinfo storeinfo = storeinfoRepository.getReferenceById(id);
        
        model.addAttribute("storeinfo", storeinfo);         
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        return "storeinfo/show";
    }    
}
