package com.shopme.accountservice.client;

import com.shopme.accountservice.model.StatisticDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "statistic-service", url = "http://localhost:9082/")
public interface StatisticService {
    // add new
    @PostMapping("/statistic")
    void add(@RequestBody StatisticDTO statisticDTO);
}
