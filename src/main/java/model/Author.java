package model;

import enums.Gender;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Author {

    private String name;
    private String surname;
    private String email;
    private Gender gender;

    private Date startDate;


}