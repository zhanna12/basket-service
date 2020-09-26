package kz.iitu.pharm.basketservice.service.impl;

import kz.iitu.pharm.basketservice.entity.Basket;
import kz.iitu.pharm.basketservice.repository.BasketRepository;
//import kz.iitu.pharm.basketservice.repository.DrugRepository;
//import kz.iitu.pharm.basketservice.repository.UserRepository;
import kz.iitu.pharm.basketservice.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class BasketServiceImpl implements BasketService {
    @Autowired
    BasketRepository basketRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    DrugRepository drugRepository;

  //  private Map<Drug, Integer> drugs = new HashMap<>();

//    @Override
//    public void addDrug(Drug drug) {
//        if (drugs.containsKey(drug)) {
//            drugs.replace(drug, drugs.get(drug) + 1);
//        } else {
//            drugs.put(drug, 1);
//        }
//    }

//    @Override
//    public void removeDrug(Drug drug) {
//        if (drugs.containsKey(drug)) {
//            if (drugs.get(drug) > 1)
//                drugs.replace(drug, drugs.get(drug) - 1);
//            else if (drugs.get(drug) == 1) {
//                drugs.remove(drug);
//            }
//        }
//    }

//    @Transactional
//    public Basket addDrugs(Long drugId){
//        Basket basket = new Basket();
//        Drug drug = drugRepository.findById(drugId).get();
//        return basketRepository.save(basket);
//    }
//
//    @Transactional
//    public boolean addBasketToUser(Long userId, Long basketId) {
//        Basket basket = basketRepository.findById(basketId).get();
//        basket.setUser(userRepository.findById(userId).get());
//        basketRepository.save(basket);
//        return true;
//    }

//    @Override
//    public Map<Drug, Integer> getDrugsInBasket() {
//        return  Collections.unmodifiableMap(drugs);
//    }

    @Transactional
    public void save(Basket basket){
        basketRepository.save(basket);
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

    @Override
    public BigDecimal getTotal() {
        return null;
    }

    @Override
    public void checkout() {

    }

//    @Override
//    public BigDecimal getTotal() {
//        return drugs.entrySet().stream()
//                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
//                .reduce(BigDecimal::add)
//                .orElse(BigDecimal.ZERO);
//    }
//
//    @Override
//    public void checkout() {
////        Product product;
////        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
////            // Refresh quantity for every product before checking
////            product = productRepository.findOne(entry.getKey().getId());
////            if (product.getQuantity() < entry.getValue())
////                throw new NotEnoughProductsInStockException(product);
////            entry.getKey().setQuantity(product.getQuantity() - entry.getValue());
////        }
////        productRepository.save(products.keySet());
////        productRepository.flush();
////        products.clear();
//    }
}

