package com.shekhovtsov.bonus.repository;

import com.shekhovtsov.bonus.model.Bonus;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BonusRepository extends CrudRepository<Bonus,Long> {

    List<Bonus> findByClientId(Long clientId);

    @Modifying
    @Query("UPDATE bonuses b SET b.is_deleted = true WHERE b.expire_date < :date;")
    void updateIsDeletedByExpireDateBefore(@Param("date") LocalDate date);

    @Query("SELECT * FROM bonuses WHERE client_id = :clientId AND is_deleted=false ORDER BY expire_date;")
    List<Bonus> findByClientIdOrderByExpirationDate(@Param("clientId") Long clientId);

    @Query("SELECT sum(amount) FROM bonuses WHERE client_id = :clientId AND is_deleted=false;")
    BigDecimal sumAmountByClientId(@Param("clientId") Long clientId);

    @Query("SELECT delete_enabled FROM Settings")
    boolean checkDeleteEnabled();

    @Modifying
    @Query("UPDATE settings SET delete_enabled = false")
    void updateDeleteEnabledToFalse();

    @Modifying
    @Query("UPDATE settings SET delete_enabled = true")
    void updateDeleteEnabledToTrue();

    @Modifying
    @Query("UPDATE bonuses SET is_deleted = true WHERE id = :id")
    void softDeleteById(@Param("id") Long id);

    @Modifying
    @Query("UPDATE bonuses SET amount = 0, is_deleted = true WHERE id = :id")
    void zeroAndSoftDelete(@Param("id") Long id);



}
