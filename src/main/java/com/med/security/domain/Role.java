package com.med.security.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity

@Data
public class Role {
    @Id
    private String code ;
    private String name;
    @OneToMany
    private Set<Permission> permissions;
}
