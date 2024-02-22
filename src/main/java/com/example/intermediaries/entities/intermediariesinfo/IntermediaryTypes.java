package com.example.intermediaries.entities.intermediariesinfo;

import jakarta.persistence.*;

@Entity
@Table
public class IntermidiaryTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private TypeOfIntermediary intermidiaryType;
    private LevelOfIntermediary intermidiaryLevel;
    //@OneToMany()
    //private List<ParentIntermediaries> parentIntermediariesList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeOfIntermediary getIntermidiaryType() {
        return intermidiaryType;
    }

    public void setIntermidiaryType(TypeOfIntermediary intermidiaryType) {
        this.intermidiaryType = intermidiaryType;
    }

    public LevelOfIntermediary getIntermidiaryLevel() {
        return intermidiaryLevel;
    }

    public void setIntermidiaryLevel(LevelOfIntermediary intermidiaryLevel) {
        this.intermidiaryLevel = intermidiaryLevel;
    }
}
