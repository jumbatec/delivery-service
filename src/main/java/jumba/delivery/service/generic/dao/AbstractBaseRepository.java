package jumba.delivery.service.generic.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


/**
 * @author Judiao Mbaua
 * 
 *        <p> This a generic repository for all crud operations</p>
 *        @NoRepositoryBean anotation avoids JPA for finding an implementation for the repository
 *
 */
@NoRepositoryBean
public interface AbstractBaseRepository<T,  ID extends Serializable>
		extends CrudRepository<T, ID> {

	List<T> findByActiveAndState(boolean active, int state);

}
