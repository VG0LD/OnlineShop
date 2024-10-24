
package com.newsilkroad.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Comparator;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AB_Product")
public class Product
        extends AbstractEntity
        implements Comparator<Product>
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Name;
    private Float price;
    private Float discount;
    private String CurrencyCode;
    private String Description;
    private  String picture_fileName;
    private int SubCategoryId;


    @Override
    public int compare(Product o1, Product o2) {
        if(o1.getPrice()==null){
            o1.setPrice(0f);
        }
        if(o2.getPrice()==null){
            o2.setPrice(0f);
        }
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
