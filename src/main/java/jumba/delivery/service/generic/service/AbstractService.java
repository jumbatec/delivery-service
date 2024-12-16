package jumba.delivery.service.generic.service;


import jumba.delivery.service.security.UserContext;

import java.io.Serializable;


/**
 * @author Judiao Mbaua
 *
 */
public interface AbstractService<T, ID extends Serializable> {

T create(UserContext userContext, T entity);

T update(UserContext userContext,T entity);

void inativate(UserContext userContext,T entity);

void activate(UserContext userContext,T entity);

void delete(UserContext userContext,T entity);

void banish(UserContext userContext,T entity);

}
