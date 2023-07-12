package com.shekhovtsov.bonus.converter;


import com.shekhovtsov.bonus.dto.BonusDto;
import com.shekhovtsov.bonus.model.Bonus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BonusConverter {


    public BonusDto entityToDto(Bonus bonus) {
        return new BonusDto(bonus.getId(), bonus.getClientId(), bonus.getAmount(), bonus.getExpireDate(), bonus.getCategory(),null);
    }

    public Bonus dtoToEntity(BonusDto bonusDto) {
        return new Bonus(bonusDto.getId(), bonusDto.getClientId(), bonusDto.getAmount(), bonusDto.getExpireDate(), bonusDto.getCategory());
    }
}
