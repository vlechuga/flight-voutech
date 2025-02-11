package es.vortech.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, updatable = false)
    @NotNull
    private Flight flight;

    @NotNull
    @Column(name = "creation_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationAt;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @JoinColumn(name = "seat_id", updatable = false)
    @ManyToOne
    private Seat seat;

    @PrePersist
    public void prePersist() {
        this.creationAt = new Date();
    }

    public enum Status {
        CANCELED, CONFIRMED
    }

}
