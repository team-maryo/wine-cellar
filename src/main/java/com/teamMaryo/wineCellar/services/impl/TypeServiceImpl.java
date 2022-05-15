package com.teamMaryo.wineCellar.services.impl;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.TypeModel;
import com.teamMaryo.wineCellar.repositories.TypeRepository;
import com.teamMaryo.wineCellar.services.TypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository repository;

    @Override
    public Iterable<TypeModel> retrieveAll(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public TypeModel create(Long userId, TypeModel type) {
        type.setTypeId(null);
        type.setUserId(userId);
        return repository.save(type);
    }

    @Override
    public TypeModel update(Long userId, Long typeId, TypeModel type) {
        if (!type.getUserId().equals(userId)) {
            return null;
        }

        if (!type.getTypeId().equals(typeId)) {
            return null;
        }

        return repository.save(type);
    }

    @Override
    public TypeModel retrieve(Long userId, Long typeId) {
        Optional<TypeModel> typeModel = repository.findByUserIdAndTypeId(userId, typeId);
        if (!typeModel.isPresent()) {
            return null;
        }
        return typeModel.get();
    }

    @Override
    public void destroy(Long userId, Long typeId) {
        TypeModel typeModel = retrieve(userId, typeId);
        if (typeModel == null) {
            return;
        }

        repository.delete(typeModel);
    }
}