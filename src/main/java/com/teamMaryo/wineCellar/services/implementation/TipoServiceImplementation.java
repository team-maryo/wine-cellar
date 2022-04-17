package com.teamMaryo.wineCellar.services.implementation;

import com.teamMaryo.wineCellar.services.TipoService;

import org.springframework.stereotype.Service;

import com.teamMaryo.wineCellar.models.TipoModel;
import com.teamMaryo.wineCellar.repositories.TipoRepository;


@Service
public class TipoServiceImplementation extends CrudServiceImplementation<TipoModel, TipoRepository> 
    implements TipoService{
    
}

