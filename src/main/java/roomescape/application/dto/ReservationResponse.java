package roomescape.application.dto;

import java.time.LocalDate;
import roomescape.domain.model.Reservation;
import roomescape.domain.model.Time;

public record ReservationResponse(Long id, String name, LocalDate date, Time time) {

  public static ReservationResponse from(Reservation reservation) {
    return new ReservationResponse(
        reservation.getId(),
        reservation.getName(),
        reservation.getDate(),
        reservation.getTime()
    );
  }
}
