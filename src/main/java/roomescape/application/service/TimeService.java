package roomescape.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.application.dto.CreateTimeRequest;
import roomescape.application.dto.DeleteTimeRequest;
import roomescape.application.dto.TimeResponse;
import roomescape.core.exception.ApplicationException;
import roomescape.core.exception.CustomErrorCode;
import roomescape.domain.model.Time;
import roomescape.domain.repository.TimeRepository;

@Service
public class TimeService {

  private final TimeRepository timeRepository;

  public TimeService(final TimeRepository timeRepository) {
    this.timeRepository = timeRepository;
  }

  public TimeResponse createTime(final CreateTimeRequest createTimeRequest) {
    final Time time = createTimeRequest.to();

    final Time save = timeRepository.save(time);

    return TimeResponse.from(save);
  }

  public List<TimeResponse> findAll() {
    return timeRepository.findAll().stream().map(TimeResponse::from).toList();
  }

  public void deleteById(final DeleteTimeRequest request) {
    final Time time = timeRepository.findById(request.id())
        .orElseThrow(() -> new ApplicationException(CustomErrorCode.TIME_ID_NOT_FOUND));

    timeRepository.deleteById(time.getId());
  }
}
