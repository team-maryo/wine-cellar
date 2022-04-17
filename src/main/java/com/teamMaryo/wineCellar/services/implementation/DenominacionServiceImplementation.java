package com.teamMaryo.wineCellar.services.implementation;

import com.teamMaryo.wineCellar.services.DenominacionService;

import org.springframework.stereotype.Service;

import com.teamMaryo.wineCellar.models.DenominacionModel;
import com.teamMaryo.wineCellar.repositories.DenominacionRepository;


@Service
public class DenominacionServiceImplementation extends CrudServiceImplementation<DenominacionModel, DenominacionRepository> 
implements DenominacionService{
    
}