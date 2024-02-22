package com.example.intermiteries.registrations;

import com.example.intermiteries.info.IntermidiaryTypes;
import jakarta.persistence.*;

@Table
@Entity
public class ChildrenIntermediaries {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title ;

    @ManyToOne()
    @JoinColumn(name = "FK_parent_intermediaries_id", referencedColumnName = "id")
    private ParentIntermediaries parentIntermediaries;

    @ManyToOne()
    @JoinColumn(name = "FK_intermediaryTypesId", referencedColumnName = "id")
    private IntermidiaryTypes intermediaryType ;

    public int getId() {
        return id;
    }

    public String getTitle() {
       return this.title ;
    }

    public void setTitle(String title) {
        this.title = getIntermediaryType().getIntermidiaryType() +
                "" + getId();
    }

    public ParentIntermediaries getParentIntermediaries() {
        return parentIntermediaries;
    }

    public void setParentIntermediaries(ParentIntermediaries parentIntermediaries) {
        this.parentIntermediaries = parentIntermediaries;
    }

    public IntermidiaryTypes getIntermediaryType() {
        return intermediaryType;
    }

    public void setIntermediaryType() {
        if(getParentIntermediaries()!=null){
            this.intermediaryType =  parentIntermediaries.getIntermidiaryType();
        }

    }
}
