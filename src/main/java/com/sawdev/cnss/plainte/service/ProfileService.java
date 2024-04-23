/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.service;

import com.sawdev.cnss.plainte.dto.ProfileDTO;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProfileService {

    ProfileDTO save(ProfileDTO profileDTO);

    List<ProfileDTO> findAll();

    ProfileDTO findById(Long id);

    ProfileDTO findByCode(String code);

    void delete(ProfileDTO profileDTO);
}
