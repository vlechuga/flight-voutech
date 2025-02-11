package es.vortech.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat")
public class Seat {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "seat_id")
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, updatable = false)
    @NotNull
    private Flight flight;

    @Column(name = "seat_code")
    @NotNull
    private String seatCode;

    @Column(name = "available")
    @NotNull
    private boolean available;

    @Version
    private int version; //optimistic locking
}
