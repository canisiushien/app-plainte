package bf.plainte.app.mapper;

import bf.plainte.app.dto.PrivilegeDTO;
import bf.plainte.app.model.Privilege;
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
