package com.teamMaryo.wineCellar.repositories;

import java.util.List;

import com.teamMaryo.wineCellar.models.ClientModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserCrudRepository<T, ID> extends JpaRepository<T, ID> {
    @Query("select e from #{#entityName} e where e.client=:client")
    List<T> findAll(ClientModel client);
}
