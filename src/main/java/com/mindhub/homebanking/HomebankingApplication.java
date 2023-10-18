package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.models.CardColor.GOLD;
import static com.mindhub.homebanking.models.CardColor.TITANIUM;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
class HomebankingApplication {

	public static void main(String[] args){
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loansRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> {
			System.out.println("Hola");

			Client client = new Client("MELBA", "Morel", "melbamorel98@gmail.com");
			clientRepository.save(client);

			Client client2 = new Client("brian", "Ochoa", "brianezequiel@gmail.com");
			clientRepository.save(client2);

			Loan loan1 = new Loan( "mortgage", 500.000,List.of(12,24,36,48,60));
			loansRepository.save(loan1);


			Loan loan2 =new Loan("Peronal", 100.000, List.of(6,12,24));
			loansRepository.save(loan2);

			Loan loan3 =new Loan("Automotive", 300.000, List.of(6,12,24,36));
			loansRepository.save(loan3);



			Account account1 = new Account("VIN001", LocalDate.now(), 5000.00);
			client.addAccount(account1);
			accountRepository.save(account1);

			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.00);
			client.addAccount(account2);
			accountRepository.save(account2);

			ClientLoan melbaL1 = new ClientLoan(400000.00, 60);
			client.addClientLoan(melbaL1);
			loan1.addClientLoan(melbaL1);
			clientLoanRepository.save(melbaL1);

			ClientLoan melbaL2 = new ClientLoan(50.000, 12);
			client.addClientLoan(melbaL2);
			loan2.addClientLoan(melbaL2);
			clientLoanRepository.save(melbaL2);

			ClientLoan brianL1 = new ClientLoan(100000.00, 24);
			client2.addClientLoan(brianL1);
			loan2.addClientLoan(brianL1);
			clientLoanRepository.save(brianL1);

			ClientLoan brianL2 = new ClientLoan(200.000, 36);
			client2.addClientLoan(brianL2);
			loan3.addClientLoan(brianL2);
			clientLoanRepository.save(brianL2);

			Card card1 = new Card(client.getFirstName(),CardType.CREDIT,GOLD,111222333, 123,LocalDate.now(),LocalDate.now());
			client.addCards(card1);
			cardRepository.save(card1);

			Card card2 = new Card(client.getFirstName(),CardType.DEBIT,TITANIUM,444555666, 321,LocalDate.now(),LocalDate.now());
			client.addCards(card2);
			cardRepository.save(card2);

			Card card3 = new Card(client2.getFirstName(),CardType.CREDIT,GOLD,111222333, 123,LocalDate.now(),LocalDate.now());
			client2.addCards(card3);
			cardRepository.save(card3);

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
