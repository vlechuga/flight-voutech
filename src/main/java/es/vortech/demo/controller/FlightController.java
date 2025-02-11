package es.vortech.demo.controller;

import es.vortech.demo.dto.FlightDto;
import es.vortech.demo.exception.ConstraintException;
import es.vortech.demo.exception.NotFoundException;
import es.vortech.demo.service.FlightService;
import es.vortech.demo.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/v1/flights")
@Tag(name = "Reservation API", description = "Example endpoints")
@AllArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final ReservationService reservationService;

    @PostMapping("/{flightCode}/seats/{numberOfSeats}")
    @Operation(summary = "Create a Flight")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<FlightDto> createFlight(@PathVariable(name="flightCode") String flightCode,
                                                  @PathVariable(name="numberOfSeats") Integer numberOfSeats) {
        final FlightDto flightDto = flightService.createFlight(flightCode, numberOfSeats);
        return new ResponseEntity<>(flightDto, HttpStatus.CREATED);
    }

    @GetMapping("/{flightCode}/seats/available")
    @Operation(summary = "Get available seats on a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    public ResponseEntity<FlightDto> getSeatsAvailable(@PathVariable String flightCode) throws NotFoundException {
        return new ResponseEntity<>(flightService.getSeatsAvailable(flightCode), HttpStatus.OK);
    }

    @PostMapping("/{flightCode}/reservations/reserve/{seatCode}")
    @Operation(summary = "Create a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "412", description = "Flight or Seat not available")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createReservation(@PathVariable(name = "flightCode") String flightCode,
                                  @PathVariable(name = "seatCode") String seatCode) throws ConstraintException, NotFoundException {
        reservationService.createReservation(flightCode, seatCode);
    }

    @PostMapping("/reservations/{reservationId}/release")
    @Operation(summary = "Cancel a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReserve(@PathVariable long reservationId) throws NotFoundException {
        reservationService.cancelReservation(reservationId);
    }

    @PostMapping("/{flightCode}/reservations/release")
    @Operation(summary = "Cancel a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelAllReserve(@PathVariable(name = "flightCode") String flightCode) throws NotFoundException {
        reservationService.cancelAllReservationsByFlightCode(flightCode);
    }

    @GetMapping("/{flightCode}/reservations/confirmed")
    @Operation(summary = "Get confirmed seats on a flight")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Flight not found")
    })
    public ResponseEntity<FlightDto> getSeatsConfirmed(@PathVariable(name = "flightCode") String flightCode) throws NotFoundException {
        return new ResponseEntity<>(reservationService.getConfirmedReservations(flightCode), HttpStatus.OK);
    }

}
