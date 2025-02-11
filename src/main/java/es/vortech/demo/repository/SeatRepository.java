package es.vortech.demo.repository;

import es.vortech.demo.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT s FROM Seat s WHERE s.flight.flightId = :flightId and s.available")
    List<Seat> findAvailableSeatsByFlightId(@Param("flightId") long flightId);

    @Query("SELECT s FROM Seat s WHERE s.flight.flightId = :flightId and s.seatCode = :seatCode and s.available")
    Optional<Seat> findSeatByFlightIdAndSeatCodeAndAvailableTrue(@Param("flightId") long flightId,
                                                                 @Param("seatCode") String seatCode);

    @Modifying
    @Query("update Seat s SET s.available = :available WHERE s.seatId = :seatId")
    void updateAvailable(@Param(value = "seatId") long seatId,
                         @Param(value = "available") boolean available);
}
