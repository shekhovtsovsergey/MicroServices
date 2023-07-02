package com.shekhovtsov.bonus.repository;

import com.shekhovtsov.bonus.model.Bonus;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BonusRepository extends CrudRepository<Bonus,Long> {

    List<Bonus> findByClientId(Long clientId);
    Integer getTotalBonusesByClientId(Long clientId);
    void deleteByExpireDateBefore(LocalDateTime dateTime);

}
