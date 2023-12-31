package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
class HomebankingApplication {
	public static void main(String[] args){
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return args -> {
			System.out.println("Hola");

			Client client = new Client("brian", "Ochoa", "brianezequiel@gmail.com");

			clientRepository.save(client);
			System.out.println(client);
			System.out.println("hola");

		};
	}
}
