package com.newsilkroad.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "AB_SubCategory")
public class SubCategory
        extends AbstractEntity
{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String Name;
    private String Description;
    private int CategoryId;

}
