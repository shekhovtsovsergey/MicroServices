package com.shekhovtsov.bonus.service;

import com.shekhovtsov.bonus.dto.BonusDto;
import com.shekhovtsov.bonus.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BonusService {
    Page<BonusDto> getBonusesByClientId(Long clientId, Pageable pageable);
    void spendBonuses(BonusDto bonusDto);

    void checkDeleteEnable();

    void deleteExpiredBonuses() throws Exception;
    void addBonus(BonusDto bonusDto) throws ResourceNotFoundException;
}
