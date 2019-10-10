package com.isa.hoteli.hoteliservice.avio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.isa.hoteli.hoteliservice.avio.model.Korisnik;


@Service
public class MailService
{

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
	 */
	@Autowired
	private Environment env;

	/*
	 * Anotacija za oznacavanje asinhronog zadatka
	 * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
	 */
	@Async
	public void sendNotificaitionAsync(Korisnik korisnik, Korisnik prijatelj, String type) throws MailException, InterruptedException {

		//Simulacija duze aktivnosti da bi se uocila razlika
		//Thread.sleep(10000);
		System.out.println("Slanje emaila...");
		
		SimpleMailMessage mail = new SimpleMailMessage();
		//String link = "https://localhost:8443/#/registracija/aktiviranjeNaloga/" + korisnik.getId(); 
		if(type.equals("FR_REQ"))
		{
			mail.setTo(prijatelj.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Zahtev za prijateljstvo");
			mail.setText("Pozdrav " + prijatelj.getIme() + ",\n\nImate novi zahtev za prijateljstvo. "
					+ "Zahtev je poslao " + korisnik.getIme() + " " + korisnik.getPrezime() + ", \nEmail adresa: " + korisnik.getEmail() + ".\n\n"
					+ "Zahtev mozete prihvatiti ili odbiti.\n\n"
					+ "Link: http://localhost:3000/account"
					+ " \n\n\n Vas MegaTravel");
			javaMailSender.send(mail);
		}
		else if(type.equals("FR_INV"))
		{
			mail.setTo(prijatelj.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Pozivnica za putovanje");
			mail.setText("Pozdrav " + prijatelj.getIme() + ",\n\nPozivam te na nezaboravno zajednicko putovanje. Pozivnicu mozes prihvatiti cime rezervises mesto u avionu. U slucaju da nisi zainteresovan/a vrlo jednostavno mozes odbiti rezervaciju. \n\n"
					+ "Svako dobro, \n " + korisnik.getIme() + " " + korisnik.getPrezime() + ", \nEmail adresa: " + korisnik.getEmail() + ".\n\n"
					+ "\nLink za rezervaciju: http://localhost:3000/userinvitations");
			javaMailSender.send(mail);
		}
		else if(type.equals("RESERVATION"))
		{
			mail.setTo(korisnik.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Uspesna rezervacija");
			mail.setText("Postovani " + korisnik.getIme() + ",\n\nObavestavamo te da je rezervacija uspesno izvrsena. Vasu rezervaciju kao i eventualne promene leta mozete pratiti na Vasem nalogu. \n\n"
					+ "Svako dobro, \nVas MegaTravel.\n\n"
					+ " \nLink za pracenje rezervacije: http://localhost:3000/account");
			javaMailSender.send(mail);
		}
		
		
		//stilizovano
//		mail.setText("<h1>Pozdrav <strong>" + agent.getIme() + "</strong></h1>,\n\nUspešno ste se registrovali na MegaTravel servis."
//				+ "Od ovoga trenutka možete aktivno postavljati oglase za izdavanje smeštaja na <a href=\"www.megatravel.com\"> našem sajtu</a>.\n"
//				+ "Parametri za prijavu: \nEmail:" + agent.getEmail() + "\nLozinka: " + agent.getLozinka());
//		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
}