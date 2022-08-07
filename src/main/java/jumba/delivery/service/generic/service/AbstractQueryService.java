package jumba.delivery.service.generic.service;

import jumba.delivery.service.generic.entity.LifeCyCleState;
import jumba.delivery.service.generic.entity.LifeCycleEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.UUID;


/**
 * @author Judiao Mbaua
 *
 */
public interface AbstractQueryService<T extends LifeCycleEntity<T>, ID extends UUID> {

Mono<T> findById(ID id);

Flux<T> findAll();

Flux<T> findByActiveAndState(LifeCyCleState lifeCyCleState);


}
