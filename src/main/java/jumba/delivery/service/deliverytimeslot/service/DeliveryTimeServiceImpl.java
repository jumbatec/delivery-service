package jumba.delivery.service.deliverytimeslot.service;

import jumba.delivery.service.deliverytimeslot.domain.DeliveryConfig;
import jumba.delivery.service.deliverytimeslot.domain.DeliveryTimeConfigEntity;
import jumba.delivery.service.deliverytimeslot.domain.DeliveryTimeEntity;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeConfigDto;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeDto;
import jumba.delivery.service.deliverytimeslot.enums.DayConfigType;
import jumba.delivery.service.deliverytimeslot.repository.DeliveryTimeConfigRepository;
import jumba.delivery.service.deliverytimeslot.repository.DeliveryTimeRepository;
import jumba.delivery.service.deliverytimeslot.resource.PageDto;
import jumba.delivery.service.mapper.DeliveryTimeConfigMapper;
import jumba.delivery.service.mapper.DeliveryTimeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jumba
 * <p>A class resposible for all the logic for delivery time slots</p>
 */
@Service
@AllArgsConstructor
@Slf4j
public class DeliveryTimeServiceImpl implements DeliveryTimeService {

    private final DeliveryTimeRepository deliveryTimeRepository;
    private final DeliveryTimeConfigRepository deliveryTimeConfigRepository;

    private final DeliveryTimeConfigMapper deliveryTimeConfigMapper;

    private final DeliveryTimeMapper deliveryTimeMapper;

    @Override
    public void updateDeliveryTimeConfig(DeliveryTimeConfigDto deliveryTimeConfigDto) {
        validateDayWindows(deliveryTimeConfigDto);
        deliveryTimeConfigRepository.save(deliveryTimeConfigMapper.fromDTO(deliveryTimeConfigDto));
    }

    @Override
    public void createDeliveryTimeConfig(DeliveryTimeConfigDto deliveryTimeConfigDto) {
        validateDayWindows(deliveryTimeConfigDto);
        deliveryTimeConfigRepository.save(deliveryTimeConfigMapper.fromDTO(deliveryTimeConfigDto));
    }

    private void validateDayWindows(DeliveryTimeConfigDto deliveryTimeConfigDto) {
        List<DeliveryConfig> deliveryConfigs = deliveryTimeConfigDto.getDeliveryConfigs();
        if (deliveryConfigs != null && deliveryConfigs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery time Config must not be empty");
        } else if (isEndHourBeforeOrEqualStartHour(deliveryConfigs)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The endHour must be greater that startHour");
        } else if (isStartTimeBeforeOrEqualLeadTime(deliveryConfigs)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The lead time must be before start hour");
        }
    }

    private boolean isEndHourBeforeOrEqualStartHour(final List<DeliveryConfig> windowConfigs) {
        return windowConfigs.stream()
                .anyMatch(windowConfig -> windowConfig.getStartTime()
                        .compareTo(windowConfig.getEndTime()) >= 0);
    }


    private boolean isStartTimeBeforeOrEqualLeadTime(final List<DeliveryConfig> windowConfigs) {
        return windowConfigs.stream()
                .anyMatch(windowConfig -> windowConfig.getLeadTime()
                        .compareTo(windowConfig.getStartTime()) >= 0);
    }

    @Override
    public Flux<DeliveryTimeDto> findDeliveryTimesByMarketId(UUID marketId, PageDto pageDto, Integer daysInAdvance) {
        final Map<LocalDate, List<DeliveryTimeEntity>> deliveryTimeMap=new HashMap<>();
        Collection<DeliveryTimeEntity> deliveryTimes = deliveryTimeRepository.findByMarketId(marketId, PageRequest.of(pageDto.getPage(), pageDto.getSize()));
        updateWindowAvailability(deliveryTimes);
        if (deliveryTimes.stream().filter(deliveryTime -> deliveryTime.isAvailable()).count() < daysInAdvance) {
            deliveryTimes = mapDeliveryTimesByDeliveryDate(deliveryTimeMap,daysInAdvance,marketId)
                    .values().stream().flatMap(Collection::stream).collect(Collectors.toList());
            updateWindowAvailability(deliveryTimes);
        }

        return Flux.fromStream(deliveryTimes.stream().map(deliveryTimeEntity -> deliveryTimeMapper.toDTO(deliveryTimeEntity))
                .sorted(Comparator.comparing(DeliveryTimeDto::getDeliveryDate))
                .limit(daysInAdvance)
                .sorted(Comparator.comparing(DeliveryTimeDto::getStartTime)));

    }

    private Map<LocalDate, List<DeliveryTimeEntity>> mapDeliveryTimesByDeliveryDate(
            final Map<LocalDate, List<DeliveryTimeEntity>> deliveryTimeMap,
            final Integer daysInAdvance,
            final UUID marketId
    ) {
        LocalDate today = LocalDate.now();
        Map<LocalDate, List<DeliveryTimeEntity>> newDeliveryTimeMap = new HashMap<>();
        for (int i = 0; i < daysInAdvance; i++) {
            if (deliveryTimeMap.containsKey(today.plusDays(i))) {
                newDeliveryTimeMap.put(today.plusDays(i), deliveryTimeMap.get(today.plusDays(i)));
                continue;
            }
            newDeliveryTimeMap.put(today.plusDays(i), generateDeliveryTimes(today.plusDays(i), marketId));
        }
        return newDeliveryTimeMap;
    }

    private List<DeliveryTimeEntity> generateDeliveryTimes(LocalDate deliveryDate, UUID marketId) {
        Flux<DeliveryTimeConfigEntity> deliveryTimeConfigs = deliveryTimeConfigRepository.findByMarketId(marketId, PageRequest.of(0, 3));
        if (!deliveryTimeConfigs.hasElements().block()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No configuration found by the given marketId");
        }

        DeliveryTimeConfigEntity deliveryTimeConfigToUse = getDeliveryConfigToUse(deliveryTimeConfigs);
        if (deliveryTimeConfigToUse != null) {
            return deliveryTimeConfigToUse.getDeliveryConfigs()
                    .stream().map(deliveryConfig ->
                            mapWindowConfigToDeliveryWindow(deliveryConfig, deliveryTimeConfigToUse, deliveryDate, marketId))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private void updateWindowAvailability(Collection<DeliveryTimeEntity> deliveryTimes) {
        deliveryTimes.stream().forEach(deliveryTime -> deliveryTime.setAvailable(!isLeadTimeExpired(deliveryTime.getLeadTime(), deliveryTime.getStartTime()
                , deliveryTime.getDeliveryDate())));
        deliveryTimeRepository.saveAll(deliveryTimes);
    }

    private boolean isLeadTimeExpired(LocalTime leadTime, LocalTime windowStart, LocalDate deliveryDate) {
        if (!deliveryDate.isAfter(LocalDate.now())) {
            long gap = Duration.between(LocalTime.now(), windowStart).toMinutes();
            long leadTimeImMinutes = Duration.between(leadTime, windowStart).toMinutes();
            return gap <= leadTimeImMinutes;
        }

        return false;

    }

    private DeliveryTimeConfigEntity getDeliveryConfigToUse(Flux<DeliveryTimeConfigEntity> deliveryTimeConfigs) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        DayConfigType configType = getConfigType(dayOfWeek, today);
        return deliveryTimeConfigs
                .toStream()
                .filter(deliveryTimeConfigEntity -> deliveryTimeConfigEntity.getDayConfigType().equals(configType))
                .findFirst().orElse(null);
    }

    private DeliveryTimeEntity mapWindowConfigToDeliveryWindow(DeliveryConfig deliveryConfig, DeliveryTimeConfigEntity configToUse, LocalDate date, UUID marketId) {
        return DeliveryTimeEntity
                .builder()
                .deliveryConfigId(configToUse.getId())
                .deliveryDate(date)
                .available(true)
                .dispatchTime(deliveryConfig.getDispatchTime())
                .endTime(deliveryConfig.getEndTime())
                .startTime(deliveryConfig.getStartTime())
                .leadTime(deliveryConfig.getLeadTime())
                .marketId(marketId)
                .build();
    }

    //TODO:Implement the logic to check weather the day is a holiday
    private boolean isHoliday(LocalDate date) {
        return false;
    }

    DayConfigType getConfigType(DayOfWeek dayOfWeek, LocalDate date) {
        if (isHoliday(date)) return DayConfigType.HOLIDAY;
        else if (dayOfWeek.equals(DayOfWeek.SUNDAY)) return DayConfigType.SUNDAY;
        else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) return DayConfigType.SATURDAY;
        return DayConfigType.WEEK_DAY;
    }

    @Override
    public Flux<DeliveryTimeConfigDto> findDeliveryTimeConfigsByMarketId(UUID marketId, PageDto pageDto) {
        return Flux.fromStream(deliveryTimeConfigRepository.findByMarketId(marketId, PageRequest.of(0, 3))
                .toStream().map(deliveryTimeConfigEntity -> deliveryTimeConfigMapper.toDTO(deliveryTimeConfigEntity)));
    }
}
