package roomescape.application.dto;

import roomescape.domain.model.Time;

public record CreateTimeRequest(String time) {
  public Time to() {
    return new Time(time);
  }
}
