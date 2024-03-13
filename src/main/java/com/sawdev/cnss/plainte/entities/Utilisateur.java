package com.sawdev.cnss.plainte.entities;

import java.time.Instant;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sawdev.cnss.plainte.configuration.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "utilisateur")
@Setter
@Getter
@ToString
public class Utilisateur extends CommonEntity {

    private static final long serialVersionUID = -8148843850199689222L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String nomUtilisateur;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Size(min = 2, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "nom", length = 50)
    private String nom;

    @Size(max = 150)
    @Column(name = "prenom", length = 150)
    private String prenom;

    @NotNull
    @Column(nullable = false)
    private boolean actif = false;

    @Size(max = 255)
    @Column(name = "activation_key", length = 255)
    @JsonIgnore
    private String activationKey;

    @Size(max = 255)
    @Column(name = "reset_key", length = 255)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @Column(name = "telephone", length = 20)
    private String telephone;

    @Column(name = "confirmation_expire_date")
    private LocalDateTime confirmationExpireDate;

    @Column(name = "reset_expire_date")
    private LocalDateTime resetExpireDate;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

}
