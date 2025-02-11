package es.vortech.demo.service.impl;

import es.vortech.demo.dto.SeatDto;
import es.vortech.demo.entity.Seat;
import es.vortech.demo.exception.ConstraintException;
import es.vortech.demo.mapper.SeatMapper;
import es.vortech.demo.repository.SeatRepository;
import es.vortech.demo.service.SeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    @Cacheable(value = "seat_cache", key = "#seatId")
    public Optional<Seat> findById(long seatId) {
        return seatRepository.findById(seatId);
    }

    @Override
    public SeatDto findAvailableSeatByFlightCodeAndSeatCode(long flightId, String seatCode) throws ConstraintException {
        final Seat seatJpa = seatRepository.findSeatByFlightIdAndSeatCodeAndAvailableTrue(flightId, seatCode)
                .orElseThrow(() -> new ConstraintException("Seat not available"));
        return SeatMapper.toDto(seatJpa);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "seat_cache", key = "#seat.seatId"),
            @CacheEvict(value = "availableSeats_cache", key = "#seat.flight.flightId")
    })
    public void updateAvailable(Seat seat, boolean available) {
        seatRepository.updateAvailable(seat.getSeatId(), available);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "seat_cache", key = "#seat.seatId"),
            @CacheEvict(value = "availableSeats_cache", key = "#seat.flight.flightId")
    })
    public void save(Seat seat) {
        seatRepository.save(seat);
    }

    @Override
    @Cacheable(value = "availableSeats_cache", key = "#flightId")
    public List<SeatDto> getSeatsAvailable(long flightId) {
        return seatRepository.findAvailableSeatsByFlightId(flightId)
                .stream()
                .map(SeatMapper::toDto)
                .toList();
    }
}
