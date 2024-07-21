package com.example.springemailexample.controller;

import com.example.springemailexample.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class NotificationController { 
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@PostMapping("/send-email")
	public String sendEmail(@RequestBody EmailDto emailDto) {
	SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
	
	simpleMailMessage.setTo(emailDto.getTo());
	simpleMailMessage.setSubject(emailDto.getSubject());
	simpleMailMessage.setText(emailDto.getText());
	
	
//	simpleMailMessage.setTo(emailDto.getTo());
//
//	simpleMailMessage.setTo(emailDto.getSubject());
//
//	simpleMailMessage.setTo(emailDto.getText());

		// changes

	javaMailSender.send(simpleMailMessage);
	
	return "Email sent successfully";
		
		
	}
	
	
	
	

}
