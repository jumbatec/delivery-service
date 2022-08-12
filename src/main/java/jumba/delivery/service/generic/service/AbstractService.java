package jumba.delivery.service.generic.service;

import jumba.delivery.service.security.UserContext;
import reactor.core.publisher.Mono;

import java.io.Serializable;


/**
 * @author Judiao Mbaua
 *
 */
public interface AbstractService<T, ID extends Serializable> {

Mono<T> create(T entity);

Mono<T> update( T entity);

void inactivate(T entity);

void activate(T entity);

void delete(T entity);

void banish(T entity);

}
