/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.mapper;

import bf.plainte.app.dto.PlainteDTO;
import bf.plainte.app.model.Plainte;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface PlainteMapper {

    PlainteDTO toDto(Plainte plainte);

    Plainte toEntity(PlainteDTO plainteDTO);
}
