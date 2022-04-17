package com.teamMaryo.wineCellar.repositories;

import com.teamMaryo.wineCellar.models.ClientModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientModel, Long> {
    
}