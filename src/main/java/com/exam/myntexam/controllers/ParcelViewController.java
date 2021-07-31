package com.exam.myntexam.controllers;

import com.exam.myntexam.config.VoucherConfig;
import com.exam.myntexam.storage.data.RuleBank;
import com.exam.myntexam.config.RuleManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author lorenzomalafo
 */
@Controller
public class ParcelViewController {
    
    @GetMapping("/parceltest")
    public String showIndex(Model model) {
        return "parceltest";
    }
    
    @GetMapping("/parcelsettings")
    public String showSettings(Model model) {
        RuleBank rule = RuleManager.getInstance().getRuleBank();
        
        model.addAttribute("reject", rule.getRejectWeight());
        model.addAttribute("heavy", rule.getHeavyWeight());
        model.addAttribute("heavycost", rule.getHeavyWeightPrice());
        model.addAttribute("smallparcel", rule.getSmallParcelLimit());
        model.addAttribute("mediumparcel", rule.getMediumParcelLimit());
        model.addAttribute("smallcost", rule.getSmallParcelPrice());
        model.addAttribute("mediumcost", rule.getMediumParcelPrice());
        model.addAttribute("largecost", rule.getLargeParcelPrice());
        
        VoucherConfig vconf = VoucherConfig.getInstance();
        
        model.addAttribute("apiurl", vconf.getVoucherUrl());
        model.addAttribute("apikey", vconf.getVoucherKey());
        
        return "parcelsettings";
    }
}
