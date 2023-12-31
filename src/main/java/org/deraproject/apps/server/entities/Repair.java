package org.deraproject.apps.server.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.deraproject.apps.server.enums.RepairTypeEnum;
import org.deraproject.apps.server.utils.converters.RepairTypeConverter;
import org.deraproject.apps.server.utils.converters.StringListConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/* Backed by Postgres native */

@Table(name = "repairs")
@Entity(name = "Repair")
@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Repair implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    @NotEmpty
    @Convert(converter = StringListConverter.class)
    private List<String> details;

    @Column(nullable = false)
    @NotEmpty
    private String itemValue;

    @Column(nullable = false)
    @NotEmpty
    private String itemAge;

    @Column(nullable = false)
    @Convert(converter = RepairTypeConverter.class)
    private RepairTypeEnum repairType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "repairs")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Volunteer> volunteers;

    @OneToMany(mappedBy = "repair", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<RepairEvent> repairEvents;

    @OneToMany(mappedBy = "repairs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Ticket> ticket;
}
