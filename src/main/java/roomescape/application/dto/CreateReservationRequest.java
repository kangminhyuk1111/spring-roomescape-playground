package roomescape.application.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import roomescape.core.exception.ApplicationException;
import roomescape.core.exception.CustomErrorCode;
import roomescape.domain.model.Reservation;
import roomescape.domain.model.Time;

public record CreateReservationRequest(String date, String name, Long time) {

  public CreateReservationRequest {
    validate();
  }

  public void validate() {
    validateReservationInfo(date, name, time);
  }

  private void validateReservationInfo(String date, String name, Long time) {
    validateDate(date);
    validateName(name);
    validateTimeId(time);
  }

  private void validateDate(String date) {
    if (date == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_DATE_REQUIRED);
    }

    if (date.trim().isEmpty()) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_DATE_REQUIRED);
    }

    LocalDate parsedDate;
    try {
      parsedDate = LocalDate.parse(date);
    } catch (DateTimeParseException e) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_DATE_INVALID_FORMAT);
    }

    if (parsedDate.isBefore(LocalDate.now())) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_DATE_PAST);
    }
  }

  private static void validateName(String name) {
    if (name == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_NAME_REQUIRED);
    }

    if (name.trim().isEmpty()) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_NAME_EMPTY);
    }
  }

  private static void validateTimeId(Long timeId) {
    if (timeId == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_TIME_REQUIRED);
    }

    if (timeId.toString().trim().isEmpty()) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_TIME_REQUIRED);
    }
  }

  public Reservation to(Time time) {
    return new Reservation(name, LocalDate.parse(date), time);
  }
}