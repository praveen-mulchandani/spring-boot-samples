package com.logging.sample.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.logging.sample.domain.Quote;

@RestController
public class LoggingController {

	@GetMapping("/quote")
	public ResponseEntity<Quote> getQuotes() {
		RestTemplate template = new RestTemplate();
		Quote quote = template.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		return new ResponseEntity<Quote>(quote, HttpStatus.OK);
	}

}
