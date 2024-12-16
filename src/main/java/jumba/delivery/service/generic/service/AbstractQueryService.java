package jumba.delivery.service.generic.service;


import jumba.delivery.service.generic.entity.LifeCyCleState;
import jumba.delivery.service.generic.entity.LifeCycleEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author Judiao Mbaua
 *
 */
public interface AbstractQueryService<T extends LifeCycleEntity<ID>, ID extends Serializable> {

Optional<T> findById(ID id);

List<T> findAll();

List<T> findByActiveAndState(LifeCyCleState lifeCyCleState);


}
