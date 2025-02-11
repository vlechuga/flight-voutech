package es.vortech.demo.mapper;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.dto.SeatDto;
import es.vortech.demo.entity.Flight;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class FlightMapper {

    public static FlightDto toDto(Flight flight) {
        if (flight == null) {
            return null;
        }

        /*final List<SeatDto> seats = Objects.nonNull(flight.getSeats())
                ? flight.getSeats().stream().map(SeatMapper::toDto).toList()
                : Collections.emptyList();*/

        return FlightDto.builder()
                .flightId(flight.getFlightId())
                .flightCode(flight.getFlightCode())
                //.seats(seats)
                .build();
    }

    public static FlightDto toShortDto(Flight flight) {
        if (flight == null) {
            return null;
        }

        return FlightDto.builder()
                .flightId(flight.getFlightId())
                .flightCode(flight.getFlightCode())
                .build();
    }

    public static Flight toEntity(FlightDto dto) {
        if (dto == null) {
            return null;
        }

        return Flight.builder()
                .flightId(dto.getFlightId())
                .flightCode(dto.getFlightCode())
                .build();
    }
}
