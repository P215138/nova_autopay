package com.nova.domain.sample.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "samples")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Builder
    public Sample(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
