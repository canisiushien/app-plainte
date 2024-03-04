package bf.plainte.app.mapper;

import bf.plainte.app.dto.ProfilDTO;
import bf.plainte.app.model.Profil;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface ProfilMapper {

    ProfilDTO toDto(Profil profil);

    Profil toEntity(ProfilDTO profilDTO);

}
