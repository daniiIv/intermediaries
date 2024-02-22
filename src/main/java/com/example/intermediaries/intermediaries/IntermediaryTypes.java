package com.example.intermediaries.intermediaries;

import com.example.intermediaries.intermediaries.additionalInfo.LevelOfIntermediary;
import com.example.intermediaries.intermediaries.additionalInfo.TypeOfIntermediary;
import com.example.intermediaries.intermediaries.parentIntermediaries.ParentIntermediaries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class IntermediaryTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private TypeOfIntermediary intermediaryType;
    @Enumerated(EnumType.STRING)
    private LevelOfIntermediary intermediaryLevel;

}
