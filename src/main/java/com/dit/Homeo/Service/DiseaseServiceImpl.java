package com.dit.Homeo.Service;

import com.dit.Homeo.Model.Disease;
import com.dit.Homeo.Repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DiseaseServiceImpl implements DiseaseService{
    @Autowired
    DiseaseRepository diseaseRepository;
    @Override
    public Disease save(Disease disease) {
        disease.setTime(LocalDateTime.now());
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        disease.setDate(dateFormat.format(date));
        diseaseRepository.save(disease);
        return disease;
    }
}
