package com.shopme.accountservice.controller;


import com.shopme.accountservice.client.NotificationService;
import com.shopme.accountservice.client.StatisticService;
import com.shopme.accountservice.entity.Account;
import com.shopme.accountservice.model.AccountDTO;
import com.shopme.accountservice.model.MessageDTO;
import com.shopme.accountservice.model.StatisticDTO;
import com.shopme.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    // Autowire Statistic service interface trong account service client
    @Autowired
    private StatisticService statisticService;

    // Autowire Notification service interface trong account service client
    @Autowired
    private NotificationService notificationService;

    // add new account service
    @PostMapping("/account")
    @PermitAll
    public AccountDTO createAccount(@RequestBody AccountDTO accountDTO) {
        accountService.add(accountDTO);
        statisticService.add(new StatisticDTO("Account " + accountDTO.getName()+ " is created successfully !", new Date()));
        // send notification
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFrom("robert.giangs@gmail.com");
        messageDTO.setTo(accountDTO.getUsername()); // username = email
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setContent("Welcome to my project, " + accountDTO.getName());
        messageDTO.setSubject("Welcome you");

        notificationService.sendEmail(messageDTO);

        return accountDTO;
    }

    // update account
    @PreAuthorize("#oauth2.hasScope('write') && hasRole('ADMIN')")
    @PutMapping("/account")
    public AccountDTO update(@RequestBody AccountDTO accountDTO) {
        accountService.update(accountDTO);
        statisticService.add(new StatisticDTO("Account " + accountDTO.getName() + " is updated successfully !", new Date()));
        return accountDTO;
    }

    // get all account
    @PreAuthorize("#oauth2.hasScope('read') && hasRole('ADMIN')")
    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        List<AccountDTO> accountDTOList = accountService.getAll();
        statisticService.add(new StatisticDTO("Get all account " , new Date()));

        return accountDTOList;
    }

    // get account by ID
    @PreAuthorize("#oauth2.hasScope('read') && hasRole('USER')")
    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable(name = "id") Long id) {

        AccountDTO accountDTO = accountService.getAccountById(id);

        if (accountDTO != null) {
            return  new ResponseEntity<>(accountDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete by Id
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/account/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        statisticService.add(new StatisticDTO("Account with ID: " + id + " deleted !", new Date()));
        accountService.delete(id);
    }

    // Return the current log in user
    @GetMapping("/me")
    public Principal me(Principal principal) {
        return principal;
    }
}
