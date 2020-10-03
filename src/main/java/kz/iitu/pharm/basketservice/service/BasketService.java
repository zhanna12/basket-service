package kz.iitu.pharm.basketservice.service;

import kz.iitu.pharm.basketservice.entity.Basket;
import kz.iitu.pharm.basketservice.entity.Drug;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface BasketService {
    Map<Drug, Integer> getDrugsInBasket();
    public List<Basket> findBasketByUserId(Long userId);
    public Basket deleteBasket(Long basketId);
    void addDrug(Drug drug);
    void removeDrug(Drug drug);
}
