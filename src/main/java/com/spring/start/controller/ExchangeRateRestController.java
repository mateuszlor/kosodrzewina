package com.spring.start.controller;


import com.spring.start.annotations.RestAPIController;
import com.spring.start.entity.ExchangeRate;
import com.spring.start.rest.ExchangeRateService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestAPIController
@Log
public class ExchangeRateRestController {

	private static final String GET_EXCHANGE_RATES = "getExchangeRates";

	@Autowired
	private ExchangeRateService exchangeRateService;

	@RequestMapping(value = GET_EXCHANGE_RATES, method = RequestMethod.GET)
	public Iterable<ExchangeRate> getExchangeRatesByRest(Model model) {
		log.info("REST: Pobrano kursy walut");
		return exchangeRateService.getExchangeRates();
	}



}
