package com.mindhub.homebanking.models;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static java.util.Optional.empty;
import static java.util.function.Predicate.not;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.data.repository.util.ClassUtils.hasProperty;
import static sun.nio.cs.Surrogate.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class RepositoriesTest {



    @Autowired
    AccountRepository accountRepository;



    //@Test

    //public void existLoans(){

        //Boolean account = accountRepository.existsByNumber();

     //   assertThat(accountRepository.existsByNumber()).is(not(empty("VIN001"))));

   // }



    //@Test

    //public void existPersonalLoan(){

       // List<Loan> loans = accountRepository.findByNumber(number);

       // assertThat(loans, hasItem(hasProperty("name", is(2))));

    //}



}