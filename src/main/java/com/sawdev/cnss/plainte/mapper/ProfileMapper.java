package com.sawdev.cnss.plainte.mapper;

import com.sawdev.cnss.plainte.dto.ProfileDTO;
import com.sawdev.cnss.plainte.entity.Profile;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDTO toDto(Profile profile);

    Profile toEntity(ProfileDTO profileDTO);

}
