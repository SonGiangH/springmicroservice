package com.shopme.statisticservice.service;

import com.shopme.statisticservice.entity.Statistic;
import com.shopme.statisticservice.model.StatisticDTO;
import com.shopme.statisticservice.repository.StatisticRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface StatisticService {
    void add(StatisticDTO statisticDTO);

    List<StatisticDTO> getAll();
}

@Transactional
@Service
class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticRepository statisticRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void add(StatisticDTO statisticDTO) {
        Statistic statistic = modelMapper.map(statisticDTO, Statistic.class);
        statisticRepo.save(statistic);
    }

    @Override
    public List<StatisticDTO> getAll() {
        // get all statistic from DB
        List<Statistic> statistics =  statisticRepo.findAll();

        // new empty DTO list
        List<StatisticDTO> statisticDTOS = new ArrayList<>();

        // model mapping and input each statistic into DTO
        statistics.forEach(stat -> {
            statisticDTOS.add(modelMapper.map(stat, StatisticDTO.class));
        });
        return statisticDTOS;
    }
}
