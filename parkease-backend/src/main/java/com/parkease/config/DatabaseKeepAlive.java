package com.parkease.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DatabaseKeepAlive {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseKeepAlive.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Runs every 5 minutes to keep the Supabase free-tier DB from sleeping
    @Scheduled(fixedRate = 300000)
    public void keepAlive() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            logger.debug("DB keep-alive ping successful");
        } catch (Exception e) {
            logger.warn("DB keep-alive ping failed: {}", e.getMessage());
        }
    }
}
