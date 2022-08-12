package model;


import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class Book {

    private String title;
    private Author author;
    private double price;
    private int count;
    private String genre;
    private User registeredUser;
    private Date registerDate;


}
