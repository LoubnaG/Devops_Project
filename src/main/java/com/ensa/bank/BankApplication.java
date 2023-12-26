package com.ensa.bank;

import com.ensa.bank.dao.ClientRepository;
import com.ensa.bank.dao.CompteRepository;
import com.ensa.bank.dao.OperationRepository;
import com.ensa.bank.entities.*;
import com.ensa.bank.metier.IBankMetierImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class BankApplication implements CommandLineRunner {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private IBankMetierImp iBankMetierImp;


    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Client c1=clientRepository.save(new Client("soumia","soumiaelkahili@gmail.com"));
        Client c2=clientRepository.save(new Client("leila","leila@gmail.com"));
        Compte cpt1=compteRepository.save(new CompteCourant("c1",new Date(), 90000, c1, 500));
        Compte cpt2=compteRepository.save(new CompteEpargne("c2",new Date(), 88000, c2, 500));

        operationRepository.save(new Versement(new Date(), 9000, cpt1));
        operationRepository.save(new Versement(new Date(), 8300, cpt1));
        operationRepository.save(new Versement(new Date(), 500, cpt1));
        operationRepository.save(new Retrait(new Date(), 3824, cpt1));

        operationRepository.save(new Versement(new Date(), 9000, cpt2));
        operationRepository.save(new Versement(new Date(), 8300, cpt2));
        operationRepository.save(new Versement(new Date(), 500, cpt2));
        operationRepository.save(new Retrait(new Date(), 3824, cpt2));


        iBankMetierImp.verser("c1",111111111);
        iBankMetierImp.virement("c1","c2",77777);

    }
}
