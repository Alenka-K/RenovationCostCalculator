package com.example.renovationcostcalculator.services;


import com.example.renovationcostcalculator.model.InfoOfFlat;
import com.example.renovationcostcalculator.repositories.InfoOfFlatRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class InfoOfFlatService {

    private final InfoOfFlatRepository infoOfFlatRepository;

    public InfoOfFlatService(InfoOfFlatRepository infoOfFlatRepository) {
        this.infoOfFlatRepository = infoOfFlatRepository;
    }

   @Transactional
    public List<InfoOfFlat> findAllByFlatId(Long id) {
        return infoOfFlatRepository.findAllByFlat_Id(id);
    }

    @Transactional
    public InfoOfFlat findById(Long id){
        return infoOfFlatRepository.findInfoOfFlatById(id);
    }

    @Transactional
    public void save(InfoOfFlat info) {
        infoOfFlatRepository.save(info);
    }

    @Transactional
    public void update(Long id, InfoOfFlat updateInfo) {
        updateInfo.setId(id);
        infoOfFlatRepository.save(updateInfo);
    }

    @Transactional
    public void delete(Long id) {

        infoOfFlatRepository.deleteById(id);
    }
}
