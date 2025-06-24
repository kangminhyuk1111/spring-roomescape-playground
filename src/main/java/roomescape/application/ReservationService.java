package roomescape.application;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.CreateReservationRequest;
import roomescape.application.dto.DeleteReservationRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.model.Reservation;
import roomescape.domain.repository.ReservationRepository;
import roomescape.infra.repository.InMemoryReservationRepository;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public ReservationService(final InMemoryReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public List<ReservationResponse> findAll() {
    System.out.println("=== ReservationService.findAll() 호출됨 ===");
    List<Reservation> reservations = reservationRepository.findAll();
    System.out.println("Repository에서 조회된 예약 수: " + reservations.size());
    return reservations.stream().map(ReservationResponse::from).toList();
  }

  public Reservation save(final CreateReservationRequest createReservationRequest) {
    final Reservation reservation = createReservationRequest.toReservation();

    return reservationRepository.save(reservation);
  }

  public void deleteById(final DeleteReservationRequest deleteReservationRequest) {
    final Reservation reservation = reservationRepository.findById(deleteReservationRequest.reservationId());

    reservationRepository.deleteById(reservation.getId());
  }
}
