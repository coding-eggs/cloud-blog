package com.cloud.blog.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAdminServer
@RestController
public class BlogAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAdminApplication.class, args);
	}



	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailProperties mailProperties;


	@GetMapping("/send")
	public void send(){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailProperties.getUsername());
		message.setTo("wsailuoli@outlook.com");
		message.setSubject("测试邮件");
		message.setText("hello world");
		mailSender.send(message);
	}
}
