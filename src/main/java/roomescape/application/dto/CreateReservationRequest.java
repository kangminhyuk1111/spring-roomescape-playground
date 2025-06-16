package roomescape.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.core.exception.ApplicationException;
import roomescape.core.exception.CustomErrorCode;
import roomescape.domain.model.Reservation;

public record CreateReservationRequest(LocalDate date, String name, LocalTime time) {

  public CreateReservationRequest {
    validateDate(date);
    validateName(name);
    validateTime(time);
  }

  private void validateDate(LocalDate date) {
    if (date == null) {
      throw new ApplicationException(CustomErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  private static void validateName(String name) {
    if (name == null) {
      throw new ApplicationException(CustomErrorCode.INTERNAL_SERVER_ERROR);
    }

    if (name.trim().isEmpty()) {
      throw new ApplicationException(CustomErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  private static void validateTime(LocalTime time) {
    if (time == null) {
      throw new ApplicationException(CustomErrorCode.INTERNAL_SERVER_ERROR);
    }
  }

  public Reservation toReservation(Long id) {
    return new Reservation(id, name, date, time);
  }
}
