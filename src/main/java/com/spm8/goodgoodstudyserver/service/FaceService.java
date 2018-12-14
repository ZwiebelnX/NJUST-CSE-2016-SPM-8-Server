package com.spm8.goodgoodstudyserver.service;

import com.spm8.goodgoodstudyserver.domain.FaceBean;
import com.spm8.goodgoodstudyserver.repository.FaceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
@Service
public class FaceService {
    @Resource
    private FaceRepository faceRepository;
    @Transactional
    public void save(FaceBean user){
        faceRepository.save(user);
    }
}
