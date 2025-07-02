package roomescape.application.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationViewController {

  private static final String VIEW_RESERVATION_V1 = "reservation";
  private static final String VIEW_RESERVATION_V2 = "new-reservation";

  @GetMapping("/reservation")
  public String reservation() {
    return VIEW_RESERVATION_V2;
  }
}
