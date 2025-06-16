package roomescape.domain.repository;

import java.util.List;
import roomescape.domain.model.Reservation;

public interface ReservationRepository {

  Long nextId();

  List<Reservation> findAll();

  Reservation save(Reservation reservation);
}
