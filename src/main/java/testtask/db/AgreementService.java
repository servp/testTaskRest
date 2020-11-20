package testtask.db;

import org.springframework.stereotype.Component;
import testtask.entity.Agreement;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class AgreementService implements DataBaseInterface {

    static AtomicInteger sequence = new AtomicInteger(3);

    private List<Agreement> dataBase = new CopyOnWriteArrayList<>();

    public void setDataBaseInClass(CopyOnWriteArrayList<Agreement> dataBase) {
        this.dataBase = dataBase;
    }

    @PostConstruct
    public void init() {
        List<Agreement> listAgreement =new ArrayList<>();
        Agreement agreement1 = new Agreement(11, 22, new BigDecimal("100.1"), new Date());
        agreement1.setAgreementId(1);
        Agreement agreement2 = new Agreement(33, 23, new BigDecimal("220.1"), new Date());
        agreement2.setAgreementId(2);
        Agreement agreement3 = new Agreement(1122, 1122, new BigDecimal("1220.1"), new Date());
        agreement3.setAgreementId(3);
        listAgreement.add(agreement1);
        listAgreement.add(agreement2);
        listAgreement.add(agreement3);
        dataBase = new CopyOnWriteArrayList<>(listAgreement);
    }


    @Override
    public Optional<Agreement> findById(int id) {
        return dataBase.stream().filter(x -> x.getAgreementId().equals(id)).findFirst();
    }

    @Override
    public Agreement save(Agreement agreement) {
        Integer idAgreement = sequence.addAndGet(1);
        sequence = new AtomicInteger(idAgreement);
        agreement.setAgreementId(idAgreement);
        dataBase.add(agreement);
        return agreement;


    }

    @Override
    public boolean deleteById(int id) {
        return dataBase.removeIf(agreement -> agreement.getAgreementId().equals(id));
    }


    @Override
    public List<Agreement> findAll() {
        return dataBase;
    }


    @Override
    public List<Agreement> findByClientIdAndProductId(Integer clientId, Integer productId) {

        List<Agreement> listFilteredAgreements = dataBase.stream().filter(x -> x.getClientId().equals(clientId) &&
                x.getProductId().equals(productId)).collect(Collectors.toList());
        return listFilteredAgreements;
    }
}
