package es.vortech.demo.mapper;

import es.vortech.demo.dto.SeatDto;
import es.vortech.demo.entity.Seat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SeatMapper {

    public static SeatDto toDto(Seat seat) {
        if (seat == null) {
            return null;
        }
        return SeatDto.builder()
                .seatId(seat.getSeatId())
                .flight(FlightMapper.toShortDto(seat.getFlight()))
                .seatCode(seat.getSeatCode())
                .available(seat.isAvailable())
                .build();
    }

    public static Seat toEntity(SeatDto dto) {
        if (dto == null) {
            return null;
        }

        return Seat.builder()
                .seatId(dto.getSeatId())
                .flight(FlightMapper.toEntity(dto.getFlight()))
                .available(dto.isAvailable())
                .seatCode(dto.getSeatCode())
                .build();
    }
}
