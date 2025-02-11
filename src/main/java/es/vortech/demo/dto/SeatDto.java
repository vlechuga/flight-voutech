package es.vortech.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeatDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long seatId;
    @JsonIgnore
    private FlightDto flight;
    private String seatCode;
    private boolean available;
}
