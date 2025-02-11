package es.vortech.demo.mapper;

import es.vortech.demo.dto.ReservationDto;
import es.vortech.demo.entity.Reservation;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReservationMapper {

    public static ReservationDto toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        return ReservationDto.builder()
                .reservationId(reservation.getReservationId())
                .flight(FlightMapper.toDto(reservation.getFlight()))
                .seatCode(reservation.getSeat().getSeatCode())
                .status(reservation.getStatus().name())
                .createAt(reservation.getCreationAt())
                .build();
    }

    public static Reservation toEntity(ReservationDto dto) {
        if (dto == null) {
            return null;
        }

        return Reservation.builder()
                .reservationId(dto.getReservationId())
                .flight(FlightMapper.toEntity(dto.getFlight()))
                .build();
    }
}
