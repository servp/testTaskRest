package testtask.db;

import testtask.entity.Agreement;

import java.util.List;
import java.util.Optional;


public interface DataBaseInterface {
    Optional<Agreement> findById(int id);

    Agreement save(Agreement agreement);

    boolean deleteById(int id);


    List<Agreement> findAll();

    List<Agreement> findByClientIdAndProductId(Integer clientId, Integer productId);
}
