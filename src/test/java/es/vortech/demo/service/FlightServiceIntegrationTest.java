package es.vortech.demo.service;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.dto.RequestDto;
import es.vortech.demo.exception.ConstraintException;
import es.vortech.demo.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlightServiceIntegrationTest {
    public static final String FLIGHT_CODE = "AB-2";
    @Autowired
    private FlightService flightService;
    @Autowired
    private ReservationService reservationService;

    @Test
    @Order(1)
    public void createInstances() {
        flightService.createFlight(FLIGHT_CODE, 3);
    }

    @Test
    @Order(2)
    public void findFlightByFlightCode() throws NotFoundException {
        FlightDto flightDto = flightService.findFlightByFlightCode(FLIGHT_CODE);
        Assertions.assertEquals(FLIGHT_CODE, flightDto.getFlightCode());
    }

    @Test
    @Order(3)
    public void getSeatsAvailable() throws NotFoundException {
        FlightDto flightDto = flightService.getSeatsAvailable(FLIGHT_CODE);
        Assertions.assertEquals(3, flightDto.getSeats().size());
    }

    @Test
    @Order(4)
    public void createReservation() throws NotFoundException, ConstraintException, InterruptedException {
        flightService.createFlight(FLIGHT_CODE, 3);
        reservationService.createReservation(FLIGHT_CODE, "A1");
        reservationService.createReservation(FLIGHT_CODE, "A1");
        reservationService.createReservation(FLIGHT_CODE, "A1");
        reservationService.createReservation(FLIGHT_CODE, "A1");
        reservationService.createReservation(FLIGHT_CODE, "A1");

        Thread.sleep(4000);

        FlightDto flightDto = reservationService.getConfirmedReservations(FLIGHT_CODE);
        Assertions.assertEquals(1, flightDto.getReservations().size());
    }

    @Test
    @Order(5)
    public void getSeatsAvailable2() throws NotFoundException {
        FlightDto flightDto = flightService.getSeatsAvailable(FLIGHT_CODE);
        Assertions.assertEquals(2, flightDto.getSeats().size());
    }

    @Test
    @Order(6)
    public void cancelAllReservationsByFlightCode() throws NotFoundException {
        reservationService.cancelAllReservationsByFlightCode(FLIGHT_CODE);
        FlightDto flightDto = reservationService.getConfirmedReservations(FLIGHT_CODE);
        Assertions.assertEquals(0, flightDto.getReservations().size());

        flightDto = flightService.getSeatsAvailable(FLIGHT_CODE);
        Assertions.assertEquals(3, flightDto.getSeats().size());
    }

    @Test
    @Order(7)
    public void processReservation () throws NotFoundException {
        FlightDto flightDto = flightService.getSeatsAvailable(FLIGHT_CODE);

        RequestDto requestDto = RequestDto.builder().flightId(flightDto.getFlightId()).seatId(flightDto.getSeats().get(0).getSeatId()).build();
        reservationService.processReservation(requestDto);
        flightDto = reservationService.getConfirmedReservations(FLIGHT_CODE);
        Assertions.assertEquals(1, flightDto.getReservations().size());
    }
}