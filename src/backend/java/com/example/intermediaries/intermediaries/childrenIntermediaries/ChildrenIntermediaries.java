package com.example.intermediaries.intermediaries.childrenIntermediaries;

import com.example.intermediaries.intermediaries.IntermediaryTypes;
import com.example.intermediaries.intermediaries.parentIntermediaries.ParentIntermediaries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChildrenIntermediaries {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title ;

    @ManyToOne()
    @JoinColumn(name = "FK_parent_intermediaries_id", referencedColumnName = "id")
    private ParentIntermediaries parentIntermediaries;

    @ManyToOne()
    @JoinColumn(name = "FK_intermediaryTypesId", referencedColumnName = "id")
    private IntermediaryTypes intermediaryType ;

}
