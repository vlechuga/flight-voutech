package es.vortech.demo.service;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.entity.Flight;
import es.vortech.demo.entity.Seat;
import es.vortech.demo.exception.NotFoundException;
import es.vortech.demo.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FlightServiceImplTest {

    @MockBean
    FlightRepository flightRepository;
    @Autowired
    FlightService flightService;

    @Test
    void findFlightByFlightCodeTest() throws NotFoundException {
        List<Seat> seats = List.of(Seat.builder().seatId(1L).seatCode("A1").available(true).build());
        Optional<Flight> flight = Optional.of(Flight.builder().flightId(1L).flightCode("A-1")
                .seats(seats).build());
        when(flightRepository.findFlightByFlightCode("A-1")).thenReturn(flight);

        final FlightDto flightDto = flightService.findFlightByFlightCode("A-1");
        assertEquals("A-1", flightDto.getFlightCode());

        assertThrows(NotFoundException.class, () -> {
            flightService.findFlightByFlightCode("A-2");
        });
    }

    @Test
    void createFlightTest() {
        Flight flight = Flight.builder().flightId(1L).flightCode("A-1").build();
        when(flightRepository.save(any())).thenReturn(flight);

        FlightDto flightDto = flightService.createFlight("A-1", 3);
        assertEquals("A-1", flightDto.getFlightCode());
    }

    @Test
    void getSeatsAvailable() throws NotFoundException {
        List<Seat> seats = List.of(Seat.builder().seatId(1L).seatCode("A1").available(true).build());
        Optional<Flight> flight = Optional.of(Flight.builder().flightId(1L).flightCode("A-1")
                .seats(seats).build());

        when(flightRepository.findFlightByFlightCode("A-1")).thenReturn(flight);
        FlightDto flightDto = flightService.getSeatsAvailable("A-1");

        assertEquals("A-1", flightDto.getFlightCode());
        assertEquals(1, flightDto.getSeats().size());
        assertThrows(NotFoundException.class, () -> {
            flightService.getSeatsAvailable("A-2");
        });
    }

}
