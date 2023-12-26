package com.ensa.bank.metier;

import com.ensa.bank.entities.Compte;
import com.ensa.bank.entities.Operation;
import org.springframework.data.domain.Page;

public interface IBankMetier {
    public Compte consulterCompte(String codeCpte);
    public void verser(String codeCpte, double montant);
    public void retirer(String codeCpte, double montant);
    public void virement(String codeCpte1, String codeCpte2, double montant);
    public Page<Operation> listOperation(String codeCpte,int page,int size);



}
