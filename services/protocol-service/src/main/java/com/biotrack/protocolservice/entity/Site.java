package com.biotrack.protocolservice.entity;

import com.biotrack.protocolservice.enums.SiteStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long siteId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String investigatorId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SiteStatus status;

    // ✅ Many-to-Many relationship with Protocol
    @ManyToMany(mappedBy = "sites")
    private List<Protocol> protocols = new ArrayList<>();
}
