/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service.impl;

import bf.plainte.app.dto.PrivilegeDTO;
import bf.plainte.app.mapper.PrivilegeMapper;
import bf.plainte.app.repository.PrivilegeRepository;
import bf.plainte.app.service.PrivilegeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository repository;

    @Autowired
    private PrivilegeMapper mapper;

    @Override
    public List<PrivilegeDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(p -> mapper.toDto(p))
                .collect(Collectors.toList());
    }

}
