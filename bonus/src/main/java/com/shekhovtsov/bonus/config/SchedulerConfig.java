package com.shekhovtsov.bonus.config;

import com.shekhovtsov.bonus.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

public class SchedulerConfig {

    @Autowired
    private BonusService bonusService;

    @Value(value = "${scheduler.deleteExpiredBonuses}")
    private String deleteExpiredBonuses;

    @Bean
    public TaskScheduler scheduler() throws Exception {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.initialize();
        Runnable deleteExpiredBonusesTask = this::deleteExpiredBonuses;
        scheduler.schedule(deleteExpiredBonusesTask, new CronTrigger(deleteExpiredBonuses));
        return scheduler;
    }

    private void deleteExpiredBonuses() {
        try {
            bonusService.deleteExpiredBonuses();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}