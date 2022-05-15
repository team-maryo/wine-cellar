package com.teamMaryo.wineCellar.services.impl;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.TipoModel;
import com.teamMaryo.wineCellar.repositories.TypeRepository;
import com.teamMaryo.wineCellar.services.TipoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoServiceImpl implements TipoService {
    @Autowired
    private TypeRepository repository;

    @Override
    public Iterable<TipoModel> retrieveAll(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public TipoModel create(Long userId, TipoModel tipo) {
        tipo.setTipoId(null);
        tipo.setUserId(userId);
        return repository.save(tipo);
    }

    @Override
    public TipoModel update(Long userId, Long tipoId, TipoModel tipo) {
        if (!tipo.getUserId().equals(userId)) {
            return null;
        }

        if (!tipo.getTipoId().equals(tipoId)) {
            return null;
        }

        return repository.save(tipo);
    }

    @Override
    public TipoModel retrieve(Long userId, Long tipoId) {
        Optional<TipoModel> tipoModel = repository.findByUserIdAndTipoId(userId, tipoId);
        if (!tipoModel.isPresent()) {
            return null;
        }
        return tipoModel.get();
    }

    @Override
    public void destroy(Long userId, Long tipoId) {
        TipoModel tipoModel = retrieve(userId, tipoId);
        if (tipoModel == null) {
            return;
        }

        repository.delete(tipoModel);
    }
}