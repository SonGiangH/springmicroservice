package com.shopme.statisticservice.controller;

import com.shopme.statisticservice.model.StatisticDTO;
import com.shopme.statisticservice.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticService service;

    // add new
    @PreAuthorize("#oauth2.hasScope('log')")
    @PostMapping("/statistic")
    public StatisticDTO add(@RequestBody StatisticDTO statisticDTO) {
        logger.debug("add new");

        service.add(statisticDTO);
        return statisticDTO;
    }

    // get all
    @PreAuthorize("#oauth2.hasScope('read') && hasRole('ADMIN')")
    @GetMapping("/statistics")
    public List<StatisticDTO> getAll() {
        logger.debug("get All");
        return service.getAll();
    }
}
