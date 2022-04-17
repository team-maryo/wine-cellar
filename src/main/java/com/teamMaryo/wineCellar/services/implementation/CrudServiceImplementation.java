package com.teamMaryo.wineCellar.services.implementation;

import java.util.List;
import java.util.Optional;

import com.teamMaryo.wineCellar.models.ClientModel;
import com.teamMaryo.wineCellar.models.Clientelable;
import com.teamMaryo.wineCellar.repositories.ClientRepository;
import com.teamMaryo.wineCellar.repositories.UserCrudRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class CrudServiceImplementation<T extends Clientelable, Repository extends UserCrudRepository<T, Long>> {
    @Autowired
    private Repository repository;

    @Autowired
    private ClientRepository userRepository;

    public Optional<ClientModel> getClient(Long clientId) {
        return userRepository.findById(clientId);
    }

    public List<T> retrieveAll(Long clientId) {
        Optional<ClientModel> client = getClient(clientId);
        if (client.isPresent()) {
            return repository.findAll(client.get());
        } else {
            return null;
        }
    }

    public T create(Long clientId, T model) {
        Optional<ClientModel> client = getClient(clientId);
        if (client.isPresent()) {
            model.setId(null);
            model.setClient(client.get());
            return repository.saveAndFlush(model);
        }
        return null;
    }

    public T update(Long clientId, Long modelId, T model) {
        Optional<ClientModel> client = getClient(clientId);
        Optional<T> testModel = repository.findById(modelId);
        if (client.isPresent() && testModel.isPresent()) {
            model.setClient(client.get());
            model.setId(modelId);
            
            if (testModel.get().equals(model)) {
                return repository.saveAndFlush(model);
            }
        }

        return null;
    }

    public T retrieve(Long clientId, Long modelId) {
        Optional<ClientModel> client = getClient(clientId);
        Optional<T> testModel = repository.findById(modelId);
        if (client.isPresent() && testModel.isPresent()) {
            if (client.get().equals(testModel.get().getClient())) {
                return testModel.get();
            }
        }
        return null;
    }

    public void destroy(Long clientId, Long modelId) {
        Optional<ClientModel> client = getClient(clientId);
        Optional<T> testModel = repository.findById(modelId);
        if (client.isPresent() && testModel.isPresent()) {
            if (client.get().equals(testModel.get().getClient())) {
                repository.delete(testModel.get());
            }
        }
    }

    public Repository getRepository() {
        return repository;
    }
}
