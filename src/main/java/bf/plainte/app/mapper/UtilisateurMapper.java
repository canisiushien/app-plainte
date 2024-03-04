package bf.plainte.app.mapper;

import bf.plainte.app.dto.UtilisateurDTO;
import bf.plainte.app.model.Utilisateur;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    UtilisateurDTO toDto(Utilisateur utilisateur);

    Utilisateur toEntity(UtilisateurDTO utilisateurDTO);

    //UtilisateurAuthDTO toAuthDto(Utilisateur utilisateur);
}
