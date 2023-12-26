package com.ensa.bank.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("R")
public class Retrait extends Operation{

    public Retrait() {
        super();
    }

    public Retrait(Date dateOperation, double montant, Compte compte) {
        super(dateOperation, montant, compte);
    }
}
