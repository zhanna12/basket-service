package kz.iitu.pharm.basketservice.service.impl;

import kz.iitu.pharm.basketservice.entity.Basket;
import kz.iitu.pharm.basketservice.entity.Drug;
import kz.iitu.pharm.basketservice.repository.BasketRepository;
//import kz.iitu.pharm.basketservice.repository.DrugRepository;
//import kz.iitu.pharm.basketservice.repository.UserRepository;
import kz.iitu.pharm.basketservice.repository.DrugRepository;
import kz.iitu.pharm.basketservice.repository.UserRepository;
import kz.iitu.pharm.basketservice.service.BasketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BasketServiceImpl implements BasketService {
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
DrugRepository drugRepository;

    Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);
   private Map<Drug, Integer> drugs = new HashMap<>();

    @Override
    public Map<Drug, Integer> getDrugsInBasket() {
        return Collections.unmodifiableMap(drugs);
    }

    public Optional<Basket> getBasketItems(long id) {

        return basketRepository.findById(id);
    }

    @Override
    public List<Basket> findBasketByUserId(Long userId){
        return basketRepository.findBasketById(userId);
    }

    @Override
    public void addDrug(Drug drug) {
        if (drugs.containsKey(drug)) {
            drugs.replace(drug, drugs.get(drug) + 1);
        } else {
            drugs.put(drug, 1);
        }
    }

    @Override
    public void removeDrug(Drug drug) {
        if (drugs.containsKey(drug)) {
            if (drugs.get(drug) > 1)
                drugs.replace(drug, drugs.get(drug) - 1);
            else if (drugs.get(drug) == 1) {
                drugs.remove(drug);
            }
        }
    }

    @Override
    public Basket deleteBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId).get();
        basketRepository.delete(basket
        );
        return basket;
    }
    @Transactional
    public void clear(){
        for(Basket b: basketRepository.findAll()){
            b.setUser(null);
            b.setId(null);
            basketRepository.save(b);
        }
        basketRepository.deleteAll();
    }

    @Transactional
    public boolean addBasketToUser(Long userId, Long basketId) {
        Basket basket = basketRepository.findById(basketId).get();
        basket.setUser(userRepository.findById(userId).get());
        basketRepository.save(basket);
        return true;
    }
}

