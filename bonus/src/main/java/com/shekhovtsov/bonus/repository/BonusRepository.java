package com.shekhovtsov.bonus.repository;

import com.shekhovtsov.bonus.model.Bonus;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BonusRepository extends CrudRepository<Bonus,Long> {

    List<Bonus> findByClientId(Long clientId);
    Integer getTotalBonusesByClientId(Long clientId);
    @Modifying
    @Query("DELETE FROM Bonus b WHERE b.expireDate < :date FOR UPDATE")
    void deleteByExpireDateBefore(@Param("date") LocalDateTime dateTime);
}
