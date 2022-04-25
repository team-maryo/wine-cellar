package com.teamMaryo.wineCellar.repositories;

import java.util.List;

import com.teamMaryo.wineCellar.models.ClientModel;
import com.teamMaryo.wineCellar.models.WineModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends UserCrudRepository<WineModel, Long> {
    @Query("select e from WineModel e where e.client=:client and e.id=:wineId")
    public List<WineModel> exists(ClientModel client, Long wineId);
}