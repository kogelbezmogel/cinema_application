package com.cinema.service;

import com.cinema.bodies.BasicSitsInfo;
import com.cinema.model.Sit;
import com.cinema.repository.SitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Service layer for sit endpoints.
 */
@Service
public class SitService {

    SitRepository sitRepository;

    @Autowired
    public SitService(SitRepository sitRepository) {
        this.sitRepository = sitRepository;
    }

    public List<Sit> findAll() {
        return sitRepository.findAll();
    }

    public List<Sit> getSitByRoom_Id(Long room_id) {
        return sitRepository.getSitByRoom_Id(room_id);
    }

    public List<BasicSitsInfo> getBasicSitsInfoByShowId(Long show_id) {
        Integer size = sitRepository.getAmountOfPlaceByShowId(show_id);
        List<Integer> all_sits = sitRepository.getSitsByShowId(show_id); // this sits should be set as available
        List<Integer> taken_sits = sitRepository.getSitsTakenByShowId(show_id); // this sits should be set as taken

        List<BasicSitsInfo> response;
        response = IntStream.range(0, size).mapToObj(num -> new BasicSitsInfo(num, "passage") ).toList();
        response = response.stream().map( sit -> {
            if ( taken_sits.contains( sit.getOrder_num() ) )
                return new BasicSitsInfo(sit.getOrder_num(), "taken");
            else if ( all_sits.contains( sit.getOrder_num()) )
                return new BasicSitsInfo(sit.getOrder_num(), "available");
            else
                return sit;
        }).toList();

        return response;
    }
}
