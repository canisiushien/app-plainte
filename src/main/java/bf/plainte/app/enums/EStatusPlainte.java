/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
public enum EStatusPlainte {

    EN_COURS(0, "En cours"),
    TRAITEE(1, "Traitée"),
    ANNULEE(2, "Annulée");

    private Integer valeur;
    private String libelle;

    EStatusPlainte(Integer pValeur, String pLibelle) {
        this.valeur = pValeur;
        this.libelle = pLibelle;
    }

    public static EStatusPlainte getByValeur(Integer value) {
        return Stream.of(EStatusPlainte.values()).filter(val -> val.getValeur().equals(value)).findAny().orElse(null);
    }

    public static EStatusPlainte getByLibelle(String label) {
        return Stream.of(EStatusPlainte.values()).filter(val -> val.getLibelle().equals(label)).findAny().orElse(null);
    }

    public static String getLibelleByValeur(Integer value) {
        return Optional.ofNullable(getByValeur(value)).map(EStatusPlainte::getLibelle).orElse(null);
    }

    public static Integer getValeurByLibelle(String label) {
        return Optional.ofNullable(getByLibelle(label)).map(EStatusPlainte::getValeur).orElse(null);
    }

    public static List<Map<String, Object>> getLibelleAsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (EStatusPlainte val : EStatusPlainte.values()) {
            Map<String, Object> local = new HashMap<>();
            local.put("valeur", val.toString());
            local.put("libelle", val.getLibelle());
            result.add(local);
        }
        return result;
    }
}
