package com.spring.start.service;

import com.spring.start.entity.Rent;
import com.spring.start.repository.RentRepository;
import com.spring.start.service.dto.CarDto;
import com.spring.start.service.dto.RentDto;
import com.spring.start.service.dto.UserDto;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vertig0 on 09.04.2017.
 */
@Transactional
@Service
@Log4j
public class RentService {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerService customerService;

    public void addRent(RentDto rentDto, UserDto user) {

        Rent rent = Rent.builder()
                .car(carService.findCarById(rentDto.getCar()))
                .customer(customerService.findCustomerById(rentDto.getCustomer()))
                .startDate(convertStringToDate(rentDto.getStartDate()))
                .endDate(convertStringToDate(rentDto.getEndDate()))
                .income(rentDto.getIncome())
                .startCourse(rentDto.getStartCourse())
                .endCourse(rentDto.getEndCourse())
                .description(rentDto.getDescription())
                .createdBy(userService.getUserById(user.getId()))
                .build();

        rentRepository.save(rent);
    }

    public Iterable<Rent> findAll(){
        return rentRepository.findAll();
    }

    private Date convertStringToDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return java.sql.Date.valueOf(date);
    }
}
