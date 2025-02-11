package es.vortech.demo.repository;

import es.vortech.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByFlight_FlightIdAndStatusEquals(Long flightId, Reservation.Status status);

    @Modifying
    @Query("update Reservation r SET r.status = :status WHERE r.reservationId = :reservationId")
    void updateStatus(@Param(value = "reservationId") long reservationId,
                      @Param(value = "status") Reservation.Status status);
}
