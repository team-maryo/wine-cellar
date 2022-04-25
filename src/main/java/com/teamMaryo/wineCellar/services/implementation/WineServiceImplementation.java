package com.teamMaryo.wineCellar.services.implementation;

import java.util.List;

import com.teamMaryo.wineCellar.joins.DenominacionWineJoin;
import com.teamMaryo.wineCellar.joins.TipoWineJoin;
import com.teamMaryo.wineCellar.models.ClientModel;
import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.repositories.WineRepository;
import com.teamMaryo.wineCellar.services.WineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WineServiceImplementation extends CrudServiceImplementation<WineModel, WineRepository> 
    implements WineService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean exists(Long clientId, Long wineId) {
        ClientModel wrapper = new ClientModel();
        wrapper.setId(clientId);
        
        List<WineModel> results = this.getRepository().exists(wrapper, wineId);
        if (!results.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public List<TipoWineJoin> retreiveTipoWines() {
        String query="SELECT TIPO.ID, WINE.ID, TIPO.WINE_TIPO, WINE.NOMBRE "
            + "FROM TIPO "
            + "INNER JOIN WINE ON WINE.TIPO_ID=TIPO.ID;";
		
		
		List<TipoWineJoin> tipoWines = jdbcTemplate.query(
            query,
            (rs, rowNum) -> {
                return new TipoWineJoin(
                    rs.getLong("TIPO.ID"),
                    rs.getLong("WINE.ID"),
                    rs.getString("WINE_TIPO"),
                    rs.getString("NOMBRE"));
            }
        );
		
		
		return tipoWines;
    }

    @Override
    public List<DenominacionWineJoin> retreiveDenominacionWines() {
        String query="SELECT DENOMINACION.ID, WINE.ID, DENOMINACION.DENOMINACION, WINE.NOMBRE "
            + "FROM DENOMINACION "
            + "INNER JOIN WINE ON WINE.TIPO_ID=DENOMINACION.ID;";
		
		
		List<DenominacionWineJoin> denominacionWines = jdbcTemplate.query(
            query,
            (rs, rowNum) -> {
                return new DenominacionWineJoin(
                    rs.getLong("DENOMINACION.ID"),
                    rs.getLong("WINE.ID"),
                    rs.getString("DENOMINACION"),
                    rs.getString("NOMBRE"));
            }
        );

		return denominacionWines;
    }
}
