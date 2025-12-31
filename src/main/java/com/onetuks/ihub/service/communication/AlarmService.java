package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.entity.communication.Alarm;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.AlarmJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

  private final AlarmSender alarmSender;
  private final AlarmJpaRepository alarmRepository;

  @Scheduled(fixedDelay = 60000)
  @Transactional
  public void sendAlarm() {
    LocalDateTime now = LocalDateTime.now();

    alarmRepository.findAll()
        .forEach(alarm -> {
          alarmSender.send(alarm);
          alarmRepository.delete(alarm);
        });
  }

  @Transactional
  public Alarm create(Event event) {
    return alarmRepository.save(
        new Alarm(
            UUIDProvider.provideUUID(Alarm.TABLE_NAME),
            event,
            event.getStartDatetime().minusMinutes(event.getRemindBeforeMinutes())));
  }

  @Transactional
  public Alarm update(Event event) {
    Alarm alarm = alarmRepository.findByEvent(event).orElseThrow(EntityNotFoundException::new);
    if (alarm.getNotifyAt().isAfter(LocalDateTime.now())) {
      return alarm;
    }

    alarm.setNotifyAt(event.getStartDatetime().minusMinutes(event.getRemindBeforeMinutes()));
    return alarm;
  }

  @Transactional
  public void delete(Event event) {
    alarmRepository.deleteByEvent(event);
  }
}
