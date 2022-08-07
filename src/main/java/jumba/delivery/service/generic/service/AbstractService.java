package jumba.delivery.service.generic.service;

import jumba.delivery.service.security.UserContext;
import reactor.core.publisher.Mono;

import java.io.Serializable;


/**
 * @author Judiao Mbaua
 *
 */
public interface AbstractService<T, ID extends Serializable> {

Mono<T> create(UserContext userContext,T entity);

Mono<T> update(UserContext userContext, T entity);

void inactivate(UserContext userContext,T entity);

void activate(UserContext userContext,T enity);

void delete(UserContext userContext,T enity);

void banish(UserContext userContext,T enity);

}
