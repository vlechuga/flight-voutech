package es.vortech.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservationDto {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long reservationId;
    @JsonIgnore
    private FlightDto flight;
    private String seatCode;
    private String status;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Date createAt;
}
