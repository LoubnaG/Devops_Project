package com.ensa.bank.web;

import com.ensa.bank.entities.Compte;
import com.ensa.bank.metier.IBankMetier;
import com.ensa.bank.metier.IBankMetierImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankController {
    @Autowired
    private IBankMetier iBankMetier;

    @RequestMapping(value = "/operations")
    public String index(){

        return"comptes";
    }

    @RequestMapping(value = "/consulterCompte" , method = RequestMethod.GET)
    public String consulter(Model model , String codeCompte ,
                            @RequestParam(name = "page",defaultValue = "0") int page ,
                            @RequestParam(name = "size",defaultValue = "4") int size){
        try{
            Compte cp = iBankMetier.consulterCompte(codeCompte);
            Page listOperations = iBankMetier.listOperation(codeCompte,page,size);
            model.addAttribute("compte",cp);
            model.addAttribute("listOperations",listOperations);
            int[] pages = new int[listOperations.getTotalPages()];
            model.addAttribute("pages",pages);
            model.addAttribute("codeCompte",codeCompte);
        }catch (Exception e){
            model.addAttribute("exception","Account not found");
        }

        return "comptes";
    }
}
