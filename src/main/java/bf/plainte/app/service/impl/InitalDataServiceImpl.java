/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service.impl;

import bf.plainte.app.model.Privilege;
import bf.plainte.app.model.Profil;
import bf.plainte.app.model.Utilisateur;
import bf.plainte.app.repository.PrivilegeRepository;
import bf.plainte.app.repository.ProfilRepository;
import bf.plainte.app.repository.UtilisateurRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class InitalDataServiceImpl {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Value("${spring.application.resources.static-locations}")
    private String dataPath;

    @Transactional
    public void saveInitAuthorities() throws FileNotFoundException, IOException {
        log.debug("""
                  =================== chemin du fichier authorities ========================
                  """ + dataPath + "\n ===================================");
        if (privilegeRepository.findAll().isEmpty()) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("data/authorities.txt").getFile());
            try (BufferedReader lineReader = new BufferedReader(new FileReader(file))) {
                String lineText;
                int count = 0;
                List<Privilege> roles = new ArrayList<>();
                while ((lineText = lineReader.readLine()) != null) {
                    String[] data = lineText.split(";");
                    if (count != 0) {
                        Privilege role = new Privilege();
                        role.setId(Long.valueOf(data[0]));
                        role.setCode(data[1]);
                        role.setLibelle(data[2]);
                        roles.add(role);
                    }
                    count++;
                }
                privilegeRepository.saveAll(roles);
            }
        }
    }

    @Transactional
    public void saveInitProfil() {
        if (profilRepository.findAll().isEmpty()) {
            Set<Privilege> privileges = privilegeRepository.findAll().stream().collect(Collectors.toSet());
            privileges.addAll(privileges);
            Profil profil = new Profil();
            profil.setLibelle("Administrateur");
            profil.setId(1L);
            profil.setCode("ADMIN");
            profil.setNativeProfil(true);
            profil.setPrivileges(privileges);
            profilRepository.save(profil);
        }
    }

    @Transactional
    public void saveInitUser() {
        if (utilisateurRepository.findAll().isEmpty()) {
            Set<Profil> profils = new HashSet<>();
            profils.add(profilRepository.findByCode("ADMIN").orElse(null));
            Utilisateur user = new Utilisateur();
            user.setId(1L);
            user.setUsername("admin");
            user.setProfils(profils);
            user.setPassword(passwordEncoder.encode("admin"));
            user.setNom("admin");
            user.setPrenom("admin");
            user.setEmail("canisiushien@gmail.com");
            user.setActif(true);
            utilisateurRepository.save(user);
            System.out.println("Utilisateur " + user.getNom() + "bien créé avec le profile " + profils.toString());
        }
    }
}
