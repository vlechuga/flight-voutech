package es.vortech.demo.repository;
import es.vortech.demo.entity.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlightRepositoryTest {

    public static final String FLIGHT_CODE = "AB-01";
    @Autowired
    private FlightRepository flightRepository;

    @Test
    void testSaveFlight() {
        Flight flight = Flight.builder()
                .flightCode(FLIGHT_CODE)
                .build();
        Flight savedFlight = flightRepository.save(flight);

        assertEquals(FLIGHT_CODE, savedFlight.getFlightCode());
    }

    @Test
    void testFindFlightByFlightCode() {
        Flight flight = Flight.builder()
                .flightCode(FLIGHT_CODE)
                .build();
        flightRepository.save(flight);
        Optional<Flight> savedFlight = flightRepository.findFlightByFlightCode(FLIGHT_CODE);

        assertEquals(FLIGHT_CODE, savedFlight.get().getFlightCode());
    }
}
