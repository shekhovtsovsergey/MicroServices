package com.shekhovtsov.bonus.repository;

import com.shekhovtsov.bonus.model.Bonus;
import com.shekhovtsov.bonus.model.BonusDetail;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


public interface BonusDetailRepository extends CrudRepository<BonusDetail, Long> {

    @Query("SELECT * FROM bonus_details WHERE bonus_id = :bonusId")
    List<BonusDetail> findByBonusId(@Param("bonusId") Long bonusId);

    @Query("SELECT * FROM bonus_details WHERE order_id = :orderId")
    List<BonusDetail> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT * FROM bonus_details WHERE bonus_id = :bonusId AND order_id = :orderId")
    BonusDetail findByBonusIdAndOrderId(@Param("bonusId") Long bonusId, @Param("orderId") Long orderId);

    @Query("SELECT * FROM bonus_details WHERE amount >= :minAmount AND amount <= :maxAmount")
    List<BonusDetail> findByAmountRange(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);

    @Query("SELECT * FROM bonuses WHERE client_id = :clientId ORDER BY expire_date;")
    List<Bonus> findByClientIdOrderByExpirationDate(Long clientId);

}
