package roomescape.application.dto;

import roomescape.domain.model.Time;

public record TimeResponse(Long id, String time) {
  public static TimeResponse from(final Time time) {
    return new TimeResponse(time.getId(), time.getTime());
  }
}
