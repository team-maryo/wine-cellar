package com.teamMaryo.wineCellar.services.implementation;

import com.teamMaryo.wineCellar.services.OriginService;

import org.springframework.stereotype.Service;

import com.teamMaryo.wineCellar.models.OriginModel;
import com.teamMaryo.wineCellar.repositories.OriginRepository;


@Service
public class OriginServiceImplementation extends CrudServiceImplementation<OriginModel, OriginRepository> 
implements OriginService{
    
}