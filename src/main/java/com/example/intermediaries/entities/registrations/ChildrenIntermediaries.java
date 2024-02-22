package com.example.intermediaries.entities.registrations;

import com.example.intermediaries.entities.intermediariesinfo.IntermediaryTypes;
import jakarta.persistence.*;

@Table
@Entity
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

    public Integer getId() {
        return id;
    }

    public String getTitle() {
       return this.title ;
    }

    public void setTitle() {

            this.title = getIntermediaryType().getIntermidiaryType() +
                    "" + getId();

    }

    public ParentIntermediaries getParentIntermediaries() {
        return parentIntermediaries;
    }

    public void setParentIntermediaries(ParentIntermediaries parentIntermediaries) {
        this.parentIntermediaries = parentIntermediaries;
    }

    public IntermediaryTypes getIntermediaryType() {
        return intermediaryType;
    }

    public void setIntermediaryType(IntermediaryTypes intermediaryTypes) {
        if(getParentIntermediaries()!=null){
            this.intermediaryType = intermediaryTypes;
        }

    }
}
