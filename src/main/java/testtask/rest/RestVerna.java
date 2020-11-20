package testtask.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import testtask.db.DataBaseInterface;
import testtask.entity.Agreement;
import testtask.entity.Statistic;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("/api")
public class RestVerna {


    DataBaseInterface dataBase;

    @Autowired
    public void setDataBase(DataBaseInterface dataBase) {
        this.dataBase = dataBase;
    }


    @PostMapping("/agreements")
    public ResponseEntity<Agreement> saveAgreement(@Valid @RequestBody Agreement agreement) {

        if (agreement == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        dataBase.save(agreement);
        return new ResponseEntity<>(agreement, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handlerValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("/agreements")
    public ResponseEntity<List<Agreement>> getListAgreementsByFilter(@RequestParam(value = "clientId", required = false) Integer clientId,
                                                                     @RequestParam(value = "productId", required = false) Integer productId) {
        List<Agreement> agreementList;
        if (clientId != null && productId != null) {
            agreementList = dataBase.findByClientIdAndProductId(clientId, productId);
        } else {
            agreementList = dataBase.findAll();
        }
        return new ResponseEntity<>(agreementList, HttpStatus.OK);
    }


    @GetMapping("/agreements/{id}")
    public ResponseEntity<Agreement> getAgreement(@PathVariable Integer id) {

        Optional<Agreement> agreement = dataBase.findById(id);
        if (agreement.isPresent())
            return new ResponseEntity<>(agreement.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/agreements/{id}")
    public ResponseEntity<Agreement> deleteById(@PathVariable Integer id) {

        if (dataBase.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistic> getStatistic(@RequestParam(value = "clientId", required = false) Integer clientId,
                                                  @RequestParam(value = "productId", required = false) Integer productId) {
        List<Agreement> listFilteredAgreements;
        List<Agreement> listWithoutFilter;
        Statistic statistic;
        if (clientId != null && productId != null) {
            listFilteredAgreements = dataBase.findByClientIdAndProductId(clientId, productId);
            statistic = getStatistic(listFilteredAgreements);
            return new ResponseEntity<>(statistic, HttpStatus.OK);
        } else {
            listWithoutFilter = dataBase.findAll();
            statistic = getStatistic(listWithoutFilter);

        }
        return new ResponseEntity<>(statistic, HttpStatus.OK);
    }

    private Statistic getStatistic(List<Agreement> listAgreements) {
        Statistic statistic;
        int count = listAgreements.size();
        BigDecimal minAmount = listAgreements.stream()
                .map(Agreement::getAmount)
                .min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO);
        BigDecimal maxAmount = listAgreements.stream()
                .map(Agreement::getAmount)
                .max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO);
        BigDecimal sum = listAgreements.stream()
                .map(Agreement::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        statistic = new Statistic(count, minAmount, maxAmount, sum);
        return statistic;
    }


}
