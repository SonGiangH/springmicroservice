package com.shopme.clientregisterservice.repository;

import com.shopme.clientregisterservice.entity.ClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientDetail, String> {
}
