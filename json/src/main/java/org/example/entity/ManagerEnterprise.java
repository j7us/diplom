package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ManagerEnterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    Manager manager;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    Enterprise enterprise;
}
