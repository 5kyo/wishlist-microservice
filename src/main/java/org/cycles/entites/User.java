package org.cycles.entites;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String role;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private int active;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "wishlist",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "productId") }
    )

    private Set<Product> products;

}
