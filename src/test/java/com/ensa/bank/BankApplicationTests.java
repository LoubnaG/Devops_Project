//package com.ensa.bank;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class BankApplicationTests {
//
//    @Test
//    void contextLoads() {
//    }
//
//}
package com.ensa.bank;

import com.ensa.bank.dao.CompteRepository;
import com.ensa.bank.dao.OperationRepository;
import com.ensa.bank.entities.*;
import com.ensa.bank.metier.IBankMetierImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IBankMetierImpTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private IBankMetierImp iBankMetier;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks, si nécessaire
    }

    @Test
    void testConsulterCompte() {
        when(compteRepository.findById("123")).thenReturn(Optional.of(mock(Compte.class)));


        Compte compte = iBankMetier.consulterCompte("123");

        assertNotNull(compte);
        assertEquals("123", compte.getCodeCompte());
        assertEquals(100.0, compte.getSolde());
    }

    @Test
    void testVerser() {
        when(compteRepository.findById("123")).thenReturn(
                Optional.of(new CompteCourant("123", new Date(), 100.0, new Client("NomClient", "email@example.com"), 50.0))
        );

        iBankMetier.verser("456", 50.0);

        Compte compte = compteRepository.findById("456").orElseThrow();
        assertEquals(250.0, compte.getSolde());
    }

    @Test
    void testRetirer() {
        when(compteRepository.findById("123")).thenReturn(
                Optional.of(new CompteCourant("123", new Date(), 100.0, new Client("NomClient", "email@example.com"), 0.0)));

        when(operationRepository.save(any(Retrait.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        iBankMetier.retirer("789", 50.0);

        Compte compte = compteRepository.findById("789").orElseThrow();
        assertEquals(250.0, compte.getSolde());
    }

    @Test
    void testVirement() {
        when(compteRepository.findById("123")).thenReturn(
                Optional.of(mock(Compte.class)));
        when(compteRepository.findById("123")).thenReturn(
                Optional.of(mock(Compte.class)));

        when(operationRepository.save(any(Retrait.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        when(operationRepository.save(any(Versement.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        iBankMetier.virement("111", "222", 50.0);

        Compte compte1 = compteRepository.findById("111").orElseThrow();
        Compte compte2 = compteRepository.findById("222").orElseThrow();

        assertEquals(150.0, compte1.getSolde());
        assertEquals(150.0, compte2.getSolde());
    }
    @Test
    void testListOperation() {
        when(operationRepository.listOperatiion("333", PageRequest.of(0, 10)))
                .thenReturn(mock(Page.class));

        Page<Operation> result = iBankMetier.listOperation("333", 0, 10);

        assertNotNull(result);
        verify(operationRepository).listOperatiion("333", PageRequest.of(0, 10));
    }
}


