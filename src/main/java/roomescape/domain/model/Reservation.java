package roomescape.domain.model;

import java.time.LocalDate;

public class Reservation {

  private Long id;
  private String name;
  private LocalDate date;
  private Time time;

  public Reservation() {
  }

  public Reservation(final Long id, final String userName, final LocalDate date,
      final Time time) {
    this.id = id;
    this.name = userName;
    this.date = date;
    this.time = time;
  }

  public Reservation(final String userName, final LocalDate date, final Time time) {
    this.name = userName;
    this.date = date;
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDate() {
    return date;
  }

  public Time getTime() {
    return time;
  }
}
