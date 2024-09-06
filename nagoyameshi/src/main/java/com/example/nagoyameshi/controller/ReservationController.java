package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.nagoyameshi.entity.Memberinfo;
import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Storeinfo;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;      
    
    public ReservationController(ReservationRepository reservationRepository) {        
        this.reservationRepository = reservationRepository;              
    }    

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        Memberinfo memberinfo = userDetailsImpl.getMemberinfo();
        Page<Reservation> reservationPage = reservationRepository.findByMemberinfoOrderByCreatedAtDesc(memberinfo, pageable);
        
        model.addAttribute("reservationPage", reservationPage);         
        
        return "reservations/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        try {
            Storeinfo storeinfo = new Storeinfo();
            model.addAttribute("storeinfo", storeinfo);         
            model.addAttribute("reservationInputForm", new ReservationInputForm());
            return "storeinfo/show";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "店舗情報を取得できませんでした。");
            return "storeinfo/error";
        }
    }

}
