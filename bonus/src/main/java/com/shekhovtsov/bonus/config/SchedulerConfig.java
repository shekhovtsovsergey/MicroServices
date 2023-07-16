package com.shekhovtsov.bonus.config;

import com.shekhovtsov.bonus.service.BonusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfig {

    private final BonusService bonusService;

    @Scheduled(cron = "${scheduler.deleteExpiredBonuses}")
    public void deleteExpiredBonuses() {
        try {
            bonusService.checkDeleteEnable();
        } catch (Exception e) {
            log.error("Удаление по расписанию не выполнено", e);
        }
    }
}