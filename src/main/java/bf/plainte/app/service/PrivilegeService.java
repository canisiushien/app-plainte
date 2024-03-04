/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service;

import bf.plainte.app.dto.PrivilegeDTO;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PrivilegeService {

    List<PrivilegeDTO> findAll();
}
