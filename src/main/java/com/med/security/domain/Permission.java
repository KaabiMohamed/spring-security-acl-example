package com.med.security.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Permission {
    @Id
    private String code ;
    private String name;
}
