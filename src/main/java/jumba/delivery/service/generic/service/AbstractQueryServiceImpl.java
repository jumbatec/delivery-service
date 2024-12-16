package jumba.delivery.service.generic.service;



import jumba.delivery.service.generic.dao.AbstractBaseRepository;
import jumba.delivery.service.generic.entity.LifeCyCleState;
import jumba.delivery.service.generic.entity.LifeCycleEntity;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
public class AbstractQueryServiceImpl<T extends LifeCycleEntity<ID>, ID extends Serializable>
		implements AbstractQueryService<T, ID> {

	private final AbstractBaseRepository<T, ID> repository;

    public AbstractQueryServiceImpl(AbstractBaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
	public Optional<T> findById(ID id) {
		
		return repository.findById(id);
	}

	@Override
	public List<T> findAll() {
		return (List<T>) repository.findAll();
	}

	@Override
	public List<T> findByActiveAndState(LifeCyCleState lifeCyCleState) {
	
		return repository.findByActiveAndState(lifeCyCleState.isActive(),lifeCyCleState.getState());
	}

}
