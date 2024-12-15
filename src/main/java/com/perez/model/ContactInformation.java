package com.perez.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable    //embedded component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation {

    //social networks
    private String email;
    private String mobile;
    private String twitter;
    private String instagram;

}
