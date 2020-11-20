package testtask.db;


import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testtask.entity.Agreement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;


class AgreementServiceTest {

    private AgreementService AGREEMENT_SERVICE = new AgreementService();
    private CopyOnWriteArrayList<Agreement> list;
    private Agreement agreement;

    @BeforeEach
    public void initDB() {
        agreement = new Agreement(44, 55, new BigDecimal("333.1"), new Date());
        agreement.setAgreementId(4);
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
        list = new CopyOnWriteArrayList<>(listAgreement);
        AGREEMENT_SERVICE.setDataBaseInClass(list);
    }

    @Test
    void findByClientIdAndProductId() {
        Integer clientId = 33;
        Integer productId = 23;
        List<Agreement> expectedList = AGREEMENT_SERVICE.findByClientIdAndProductId(clientId, productId);
        Assert.assertTrue(expectedList.stream().allMatch(agreement -> agreement.getClientId().equals(clientId)
                && agreement.getProductId().equals(productId)));
    }

    @Test
    void findById_Test() {
        int id = 2;
        Optional<Agreement> agreement = AGREEMENT_SERVICE.findById(id);
        int expectedId = agreement.get().getAgreementId();
        Assertions.assertEquals(expectedId, id);
    }

    @Test
    void save_Test() {
        AGREEMENT_SERVICE.save(agreement);
        Agreement actualAgreement = AGREEMENT_SERVICE.findByClientIdAndProductId(44, 55).get(0);
        Assert.assertEquals(agreement, actualAgreement);
    }

    @Test
    void deleteById() {
        boolean result = AGREEMENT_SERVICE.deleteById(2);
        Assert.assertTrue(result);
    }

    @Test
    void findAll() {
        List<Agreement> expectedList = AGREEMENT_SERVICE.findAll();
        Assert.assertEquals(expectedList, list);
    }

}