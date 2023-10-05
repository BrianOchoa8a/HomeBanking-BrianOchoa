package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
class HomebankingApplication {
	public static void main(String[] args){
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return args -> {
			System.out.println("Hola");

			Client client = new Client("MELBA", "Morel", "melbamorel98@gmail.com");
			clientRepository.save(client);

			Client client2 = new Client("brian", "Ochoa", "brianezequiel@gmail.com");
			clientRepository.save(client2);




			Account account1 = new Account("texto", LocalDate.now(), 5000.00);
			client.addAccount(account1);
			accountRepository.save(account1);

			Account account2 = new Account("texto2", LocalDate.now().plusDays(1), 7500.00);
			client.addAccount(account2);
			accountRepository.save(account2);


			System.out.println(client);
			System.out.println(account1);
			System.out.println(account2);

		};
	}
}
