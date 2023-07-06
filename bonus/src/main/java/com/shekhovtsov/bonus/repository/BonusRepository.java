package com.shekhovtsov.bonus.repository;

import com.shekhovtsov.bonus.model.Bonus;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BonusRepository extends CrudRepository<Bonus,Long> {

    List<Bonus> findByClientId(Long clientId);

    //исправить
    BigDecimal getTotalBonusesByClientId(Long clientId);

    @Modifying
    @Query("DELETE FROM Bonus b WHERE b.expireDate < :date FOR UPDATE")
    void deleteByExpireDateBefore(@Param("date") LocalDate dateTime);

    @Query("SELECT * FROM Bonus WHERE clientId = :clientId ORDER BY expirationDate;")
    List<Bonus> findByClientIdOrderByExpirationDate(@Param("clientId") Long clientId);

}
