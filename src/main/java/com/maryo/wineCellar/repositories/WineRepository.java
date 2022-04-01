import org.springframework.data.repository.CrudRepository;
import com.maryo.wineCellar.tables.WineTable;

public interface WineRepository extends CrudRepository<WineTable, Long> {

}