package jumba.delivery.service.deliverytimeslot.service;

import jumba.delivery.service.deliverytimeslot.domain.DeliveryConfigs;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeConfigDto;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeDto;
import jumba.delivery.service.deliverytimeslot.repository.DeliveryTimeConfigRepository;
import jumba.delivery.service.deliverytimeslot.repository.DeliveryTimeRepository;
import jumba.delivery.service.deliverytimeslot.resource.PageDto;
import jumba.delivery.service.mapper.DeliveryTimeConfigMapper;
import jumba.delivery.service.mapper.DeliveryTimeMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
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

    private void validateDayWindows( DeliveryTimeConfigDto deliveryTimeConfigDto) {
        List<DeliveryConfigs> deliveryConfigs = deliveryTimeConfigDto.getDeliveryConfigs();
        if (deliveryConfigs!=null && deliveryConfigs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Delivery time Config must not be empty");
        } else if (isEndHourBeforeOrEqualStartHour(deliveryConfigs)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The endHour must be greater that startHour");
        } else if (isStartTimeBeforeOrEqualLeadTime(deliveryConfigs)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The lead time must be before start hour");
        }
    }

    private boolean isEndHourBeforeOrEqualStartHour(final List<DeliveryConfigs> windowConfigs) {
        return windowConfigs.stream()
                .anyMatch(windowConfig -> windowConfig.getStartTime()
                .compareTo(windowConfig.getEndTime()) >= 0);
    }


    private boolean isStartTimeBeforeOrEqualLeadTime(final List<DeliveryConfigs> windowConfigs) {
        return windowConfigs.stream()
                .anyMatch(windowConfig -> windowConfig.getLeadTime()
                .compareTo(windowConfig.getStartTime()) >= 0);
    }

    @Override
    public Flux<DeliveryTimeDto> findDeliveryTimesByMarketId(UUID marketId, PageDto pageDto) {
        return Flux.fromStream(deliveryTimeRepository.findByMarketId(marketId, PageRequest.of(pageDto.getPage(), pageDto.getSize()))
                .toStream().map(deliverTime->deliveryTimeMapper.toDTO(deliverTime)));
    }

    @Override
    public Flux<DeliveryTimeConfigDto> findDeliveryTimeConfigsByMarketId(UUID marketId, PageDto pageDto) {
        return Flux.fromStream(deliveryTimeConfigRepository.findByMarketId(marketId, PageRequest.of(pageDto.getPage(), pageDto.getSize()))
                .toStream().map(deliverTimeConfig->deliveryTimeConfigMapper.toDTO(deliverTimeConfig)));
    }
}
