package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoansRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
class HomebankingApplication {

	public static void main(String[] args){
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoansRepository loansRepository){
		return args -> {
			System.out.println("Hola");

			Client client = new Client("MELBA", "Morel", "melbamorel98@gmail.com");
			clientRepository.save(client);

			Client client2 = new Client("brian", "Ochoa", "brianezequiel@gmail.com");
			clientRepository.save(client2);

			Loan loan1 = new Loan( "brian", 15.00,List.of(2,3,5));
			loansRepository.save(loan1);


			Loan loan2 =new Loan("Mica", 20.00, List.of(4,5,6));
			loansRepository.save(loan2);




			Account account1 = new Account("texto", LocalDate.now(), 5000.00);
			client.addAccount(account1);
			accountRepository.save(account1);

			Account account2 = new Account("texto2", LocalDate.now().plusDays(1), 7500.00);
			client.addAccount(account2);
			accountRepository.save(account2);


			Transaction transaction1 = new Transaction(DEBIT, -35000.00, "Transfer", LocalDate.now() );
			account1.addTransaction(transaction1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(CREDIT, -35.00, "Car Fee", LocalDate.now() );
			account1.addTransaction(transaction2);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(DEBIT, 4350.00, "Netflix", LocalDate.now() );
			account1.addTransaction(transaction3);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(DEBIT, 4350.00, "Transferencia", LocalDate.now() );
			account2.addTransaction(transaction4);
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(DEBIT, 4350.00, "Transfer", LocalDate.now() );
			account2.addTransaction(transaction5);
			transactionRepository.save(transaction5);

			Transaction transaction6 = new Transaction(DEBIT, 4350.00, "Deposit", LocalDate.now() );
			account2.addTransaction(transaction6);
			transactionRepository.save(transaction6);





			System.out.println(client);
			System.out.println(account1);
			System.out.println(account2);
			System.out.println(transaction1);

		};
	}

}
