package com.sawdev.cnss.plainte.mapper;

import com.sawdev.cnss.plainte.dto.PrivilegeDTO;
import com.sawdev.cnss.plainte.entity.Privilege;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

    PrivilegeDTO toDto(Privilege privilege);

    Privilege toEntity(PrivilegeDTO privilegeDTO);

}
