/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.dto.ProfileDTO;
import com.sawdev.cnss.plainte.exception.CustomException;
import com.sawdev.cnss.plainte.mapper.ProfileMapper;
import com.sawdev.cnss.plainte.repository.ProfileDao;
import com.sawdev.cnss.plainte.service.ProfileService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao repository;

    @Autowired
    private ProfileMapper mapper;

    @Override
    public ProfileDTO save(ProfileDTO profileDTO) {
        return mapper.toDto(repository.save(mapper.toEntity(profileDTO)));
    }

    @Override
    public List<ProfileDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProfileDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(() -> new CustomException("Profile " + id + " inexistante"));
    }

    @Override
    public ProfileDTO findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDto).orElseThrow(() -> new CustomException("Profile " + code + " inexistante"));
    }

    @Override
    public void delete(ProfileDTO profileDTO) {
        repository.deleteById(profileDTO.getId());
    }
}
