package com.example.intermediaries.entities.intermediariesinfo;

import com.example.intermediaries.entities.registrations.ParentIntermediaries;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class IntermediaryTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private TypeOfIntermediary intermediaryType;
    @Enumerated(EnumType.STRING)
    private LevelOfIntermediary intermediaryLevel;
    @OneToMany()
    private List<ParentIntermediaries> parentIntermediariesList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeOfIntermediary getIntermidiaryType() {
        return intermediaryType;
    }

    public void setIntermediaryType(TypeOfIntermediary intermediaryType) {
        this.intermediaryType = intermediaryType;
    }

    public LevelOfIntermediary getIntermediaryLevel() {
        return intermediaryLevel;
    }

    public void setIntermidiaryLevel(LevelOfIntermediary intermediaryLevel) {
        this.intermediaryLevel = intermediaryLevel;
    }

    public List<ParentIntermediaries> getParentIntermediariesList() {
        return parentIntermediariesList;
    }

    public void setParentIntermediariesList(List<ParentIntermediaries> parentIntermediariesList) {
        this.parentIntermediariesList = parentIntermediariesList;
    }
}
