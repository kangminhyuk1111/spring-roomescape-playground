package roomescape.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import roomescape.core.exception.ApplicationException;
import roomescape.core.exception.CustomErrorCode;
import roomescape.domain.model.Reservation;

public record CreateReservationRequest(String date, String name, String time) {

  public CreateReservationRequest {
    validateReservationInfo(date, name, time);
  }

  private void validateReservationInfo(String date, String name, String time) {
    validateDate(date);
    validateName(name);
    validateTime(time);
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

  private static void validateTime(String time) {
    if (time == null) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_TIME_REQUIRED);
    }

    if (time.trim().isEmpty()) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_TIME_REQUIRED);
    }

    try {
      LocalTime.parse(time);
    } catch (DateTimeParseException e) {
      throw new ApplicationException(CustomErrorCode.RESERVATION_TIME_INVALID_FORMAT);
    }
  }

  public Reservation to() {
    return new Reservation(name, LocalDate.parse(date), LocalTime.parse(time));
  }
}
