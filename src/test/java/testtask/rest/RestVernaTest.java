package testtask.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import testtask.db.AgreementService;
import testtask.entity.Agreement;
import testtask.entity.Statistic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestVernaTest {

    private List<Agreement> list;
    private Agreement agreement;
    private Integer clientId;
    private Integer productId;

    @Autowired
    private RestVerna restVerna;

    @Mock
    private AgreementService dataBase;

    @Before
    public void init() {
        restVerna.setDataBase(dataBase);
    }

    @Before
    public void initListBD() {
        clientId = 1;
        productId = 2;
        agreement = new Agreement();
        agreement.setClientId(clientId);
        agreement.setProductId(productId);
        agreement.setAgreementId(1);
        list = new ArrayList<>();
        list.add(agreement);
    }

    @Test
    public void saveAgreement() {
        when(dataBase.save(agreement)).thenReturn(agreement);
        ResponseEntity<Agreement> agreementResponseEntity = restVerna.saveAgreement(new Agreement());
        Assert.assertNotNull(agreementResponseEntity);
        verify(dataBase, times(1)).save(agreement);
    }

    @Test
    public void getAgreementOk() {
        Integer id = 1;
        when(dataBase.findById(eq(id))).thenReturn(Optional.of(agreement));
        ResponseEntity<Agreement> agreementResp = restVerna.getAgreement(id);

        Assert.assertNotNull(agreementResp);
        Assert.assertEquals(agreementResp.getBody(), agreement);
        Assert.assertEquals(agreementResp.getStatusCode(), HttpStatus.OK);
        verify(dataBase, times(1)).findById(id);
    }

    @Test
    public void getAgreementNotFound() {
        Integer id = 999;
        Optional<Agreement> agreementOptional = Optional.empty();
        when(dataBase.findById(eq(id))).thenReturn(agreementOptional);
        ResponseEntity<Agreement> agreementResp = restVerna.getAgreement(id);
        Assert.assertEquals(agreementResp.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(dataBase, times(1)).findById(id);
    }

    @Test
    public void deleteById() {
        Integer id = 1;
        when(dataBase.deleteById(eq(id))).thenReturn(true);
        ResponseEntity<Agreement> agreementResp = restVerna.deleteById(id);
        Assert.assertEquals(agreementResp.getStatusCode(), HttpStatus.OK);
        verify(dataBase, times(1)).deleteById(id);
    }

    @Test
    public void getListAgreementsByFilter_NotNull() {
        when(dataBase.findByClientIdAndProductId(eq(clientId), eq(productId))).thenReturn(list);
        ResponseEntity<List<Agreement>> agreementResp = restVerna.getListAgreementsByFilter(clientId, productId);
        Assert.assertEquals(agreementResp.getStatusCode(), HttpStatus.OK);
        verify(dataBase, times(1)).findByClientIdAndProductId(clientId, productId);
    }

    @Test
    public void getListAgreementsByFilter_Null() {
        clientId = null;
        productId = null;
        when(dataBase.findAll()).thenReturn(list);
        ResponseEntity<List<Agreement>> agreementResp = restVerna.getListAgreementsByFilter(clientId, productId);
        Assert.assertEquals(agreementResp.getStatusCode(), HttpStatus.OK);
        verify(dataBase, times(1)).findAll();
    }

    @Test
    public void getStatistic_clientIdProductIdNotNull() {
        Statistic statistic = new Statistic();
        int count = 12;
        statistic.setCount(count);
        BigDecimal sum = new BigDecimal("100");
        statistic.setSum(sum);
        ResponseEntity<Statistic> statisticResp = restVerna.getStatistic(clientId, productId);
        Assert.assertEquals(statistic.getCount(), count);
        Assert.assertEquals(statistic.getSum(), sum);
        Assert.assertEquals(statisticResp.getStatusCode(), HttpStatus.OK);
        verify(dataBase, times(1)).findByClientIdAndProductId(clientId, productId);
    }

    @Test
    public void getStatistic_clientIdProductIdIsNull() {
        Statistic statistic = new Statistic();
        int count = 12;
        statistic.setCount(count);
        BigDecimal sum = new BigDecimal("100");
        statistic.setSum(sum);
        ResponseEntity<Statistic> statisticResp = restVerna.getStatistic(null, null);
        Assert.assertEquals(statistic.getCount(), count);
        Assert.assertEquals(statistic.getSum(), sum);
        Assert.assertEquals(statisticResp.getStatusCode(), HttpStatus.OK);
        verify(dataBase, times(1)).findAll();
    }

}