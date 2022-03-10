package org.cycles.entites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import java.io.Serializable;

@Entity
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "confirmationtoken")
public class ConfirmationToken implements Serializable{
    @Id
    @Column(name="confirmationId")
    private String id;

    @OneToOne(cascade = { CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })

    @JoinColumn(name = "userId")

    private User user;
}
