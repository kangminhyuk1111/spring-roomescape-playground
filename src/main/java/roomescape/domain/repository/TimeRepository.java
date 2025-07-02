package roomescape.domain.repository;

import java.util.List;
import java.util.Optional;
import roomescape.domain.model.Time;

public interface TimeRepository {

  Time save(Time time);

  List<Time> findAll();

  void deleteById(Long id);

  Optional<Time> findById(Long id);
}
