package com.shekhovtsov.bonus.service;

import com.shekhovtsov.bonus.converter.BonusConverter;
import com.shekhovtsov.bonus.dto.BonusDto;
import com.shekhovtsov.bonus.exception.ResourceNotFoundException;
import com.shekhovtsov.bonus.model.Bonus;
import com.shekhovtsov.bonus.model.BonusDetail;
import com.shekhovtsov.bonus.repository.BonusDetailRepository;
import com.shekhovtsov.bonus.repository.BonusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;
    private final BonusDetailRepository bonusDetailRepository;
    private final BonusConverter bonusConverter;
    private final int BONUS_EXP = 1;


    @Override
    public Page<BonusDto> getBonusesByClientId(Long clientId, Pageable pageable) {
        List<BonusDto> bonusDtos = bonusRepository.findByClientId(clientId)
                .stream()
                .map(bonusConverter::entityToDto)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), bonusDtos.size());
        Page<BonusDto> page = new PageImpl<>(bonusDtos.subList(start, end), pageable, bonusDtos.size());

        return page;
    }


    @Override
    public void spendBonuses(BonusDto bonusDto) {
        List<Bonus> bonuses = bonusRepository.findByClientIdOrderByExpirationDate(bonusDto.getClientId());
        BigDecimal spendAmount = bonusDto.getAmount();
        List<BonusDetail> bonusDetails = new ArrayList<>();
        for (Bonus bonus : bonuses) {

            if (bonus.getAmount().compareTo(spendAmount) > 0) {
                bonus.spend(spendAmount);
                bonusRepository.save(bonus);
                BonusDetail bonusDetail = new BonusDetail();
                bonusDetail.setBonusId(bonus.getId());
                bonusDetail.setOrderId(bonusDto.getOrderId());
                bonusDetail.setAmount(spendAmount);
                bonusDetails.add(bonusDetail);
                bonusDetailRepository.saveAll(bonusDetails);
                return;
            }

            if (bonus.getAmount().compareTo(spendAmount) == 0) {
                bonus.spend(spendAmount);
                bonusRepository.save(bonus);
                bonusRepository.softDeleteById(bonus.getId());
                BonusDetail bonusDetail = new BonusDetail();
                bonusDetail.setBonusId(bonus.getId());
                bonusDetail.setOrderId(bonusDto.getOrderId());
                bonusDetail.setAmount(spendAmount);
                bonusDetails.add(bonusDetail);
                bonusDetailRepository.saveAll(bonusDetails);
                return;
            }

            BonusDetail bonusDetail = new BonusDetail();
            bonusDetail.setBonusId(bonus.getId());
            bonusDetail.setOrderId(bonusDto.getOrderId());
            bonusDetail.setAmount(bonus.getAmount());
            bonusDetails.add(bonusDetail);
            spendAmount = spendAmount.subtract(bonus.getAmount());
            bonus.spend(bonus.getAmount());
            bonusRepository.save(bonus);
            bonusRepository.softDeleteById(bonus.getId());

        }
        bonusDetailRepository.saveAll(bonusDetails);
    }




    @Override
    public void checkDeleteEnable(){
        if (bonusRepository.checkDeleteEnabled()) {
            return;
        }
        bonusRepository.updateDeleteEnabledToTrue();
        deleteExpiredBonuses();
    }

    @Override
    @Transactional
    public void deleteExpiredBonuses(){
            bonusRepository.updateIsDeletedByExpireDateBefore(LocalDate.now());
            bonusRepository.updateDeleteEnabledToFalse();
    }


    @Override
    public void addBonus(BonusDto bonusDto) throws ResourceNotFoundException {
        LocalDate expireDate = LocalDate.from(LocalDateTime.now().plusMonths(BONUS_EXP));
        Bonus bonus = bonusConverter.dtoToEntity(bonusDto);
        try {
            bonusRepository.save(bonus);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error adding bonus"+ e.getMessage());
        }
    }
}


//подумать над восстановлением бонусов если покупка отменилась как реализовать