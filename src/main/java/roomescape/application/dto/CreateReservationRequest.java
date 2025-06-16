package roomescape.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.model.Reservation;

public record CreateReservationRequest(LocalDate date, String name, LocalTime time) {

  public Reservation toReservation(Long id) {
    return new Reservation(id, name, date, time);
  }
}
