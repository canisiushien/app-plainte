/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface InitalDataService {

    void saveInitAuthorities() throws FileNotFoundException, IOException;

    void saveInitProfil();

    void saveInitUser();
}