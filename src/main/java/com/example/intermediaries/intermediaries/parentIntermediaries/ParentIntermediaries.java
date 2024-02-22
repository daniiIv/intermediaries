package com.example.intermediaries.intermediaries.parentIntermediaries;

import com.example.intermediaries.intermediaries.IntermediaryTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ParentIntermediaries {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title ;

    @ManyToOne()
    @JoinColumn(name = "FK_intermediaryTypesId", referencedColumnName = "id")
    private IntermediaryTypes intermediaryType;

}
