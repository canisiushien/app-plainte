package com.sawdev.cnss.plainte.mapper;

import com.sawdev.cnss.plainte.dto.UserDTO;
import com.sawdev.cnss.plainte.entity.User;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

}
