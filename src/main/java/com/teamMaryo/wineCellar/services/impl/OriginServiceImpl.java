package com.teamMaryo.wineCellar.services.impl;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.OriginModel;
import com.teamMaryo.wineCellar.repositories.OriginRepository;
import com.teamMaryo.wineCellar.services.OriginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginServiceImpl implements OriginService {
    @Autowired
    private OriginRepository repository;

    @Override
    public Iterable<OriginModel> retrieveAll(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public OriginModel create(Long userId, OriginModel origin) {
        origin.setOriginId(null);
        origin.setUserId(userId);
        return repository.save(origin);
    }

    @Override
    public OriginModel update(Long userId, Long originId, OriginModel origin) {
        if (!origin.getUserId().equals(userId)) {
            return null;
        }

        if (!origin.getOriginId().equals(originId)) {
            return null;
        }

        return repository.save(origin);
    }

    @Override
    public OriginModel retrieve(Long userId, Long originId) {
        Optional<OriginModel> originModel = repository.findByUserIdAndOriginId(userId, originId);
        if (!originModel.isPresent()) {
            return null;
        }

        return originModel.get();
    }

    @Override
    public void destroy(Long userId, Long originId) {
        OriginModel origin = retrieve(userId, originId);
        if (origin == null) {
            return;
        }

        repository.delete(origin);
    }
}
