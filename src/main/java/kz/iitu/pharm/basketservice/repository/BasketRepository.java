package kz.iitu.pharm.basketservice.repository;

import kz.iitu.pharm.basketservice.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
}

