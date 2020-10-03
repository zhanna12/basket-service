package kz.iitu.pharm.basketservice.repository;

import kz.iitu.pharm.basketservice.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    Drug findByName(String name);
}
