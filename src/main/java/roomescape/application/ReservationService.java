package roomescape.application;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.ReservationResponse;
import roomescape.infra.repository.InMemoryReservationRepository;

@Service
public class ReservationService {

  private final InMemoryReservationRepository reservationRepository;

  public ReservationService(final InMemoryReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public List<ReservationResponse> findAllReservations() {
    return reservationRepository.findAll().stream().map(ReservationResponse::from).toList();
  }
}
