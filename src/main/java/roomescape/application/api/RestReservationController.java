package roomescape.application.api;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.service.ReservationService;
import roomescape.application.dto.CreateReservationRequest;
import roomescape.application.dto.DeleteReservationRequest;
import roomescape.application.dto.ReservationResponse;
import roomescape.domain.model.Reservation;

@RestController
public class RestReservationController {

  private final ReservationService reservationService;

  public RestReservationController(final ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping("/reservations")
  public List<ReservationResponse> reservations() {
    return reservationService.findAll();
  }

  @PostMapping("/reservations")
  public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest request) {
    System.out.println("request = " + request.toString());

    Reservation savedReservation = reservationService.save(request);
    ReservationResponse response = ReservationResponse.from(savedReservation);

    return ResponseEntity
        .created(URI.create("/reservations/" + savedReservation.getId()))
        .body(response);
  }

  @DeleteMapping("/reservations/{reservationId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReservation(@PathVariable Long reservationId) {
    final DeleteReservationRequest request = new DeleteReservationRequest(reservationId);
    reservationService.deleteById(request);
  }
}
