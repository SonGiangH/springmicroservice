package com.shopme.clientregisterservice.controller;


import com.shopme.clientregisterservice.entity.ClientDetail;
import com.shopme.clientregisterservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@PermitAll
@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    // Create new
    @PostMapping("/clientdetail")
    public ClientDetail addAccount(@RequestBody ClientDetail clientDetail) {
        // encrypt password
        clientDetail.setClientSecret(new BCryptPasswordEncoder().encode(clientDetail.getClientSecret()));
        clientRepository.save(clientDetail);
        return clientDetail;
    }

    // Get All
    @GetMapping("/clientdetails")
    public List<ClientDetail> getAll() {
        return clientRepository.findAll();
    }

    // Delete by id
    @DeleteMapping("/clientdetail")
    public void delete(@RequestParam(name="clientId") String clientId) {
        clientRepository.deleteById(clientId);
    }
}
