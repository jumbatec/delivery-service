package jumba.delivery.service.generic.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ILifeCycleEntity<T> {
	
	public T getId() ;

	public void setId(T id) ;

	public UUID getCreatedBy();

	public void setCreatedBy(UUID createdBy) ;

	public UUID getUpdatedBy() ;

	public void setUpdatedBy(UUID updatedBy);

	public UUID getActivatedBy() ;
	
	public void setActivatedBy(UUID activatedBy) ;
	
	public int getState() ;

	public void setState(int state);

	public boolean isActive() ;

	public void setActive(boolean active) ;

	public LocalDateTime getCreatedAt() ;

	public void setCreatedAt(LocalDateTime createdAt) ;

	public LocalDateTime getUpdatedAt();

	public void setUpdatedAt(LocalDateTime updatedAt) ;

	public LocalDateTime getActivatedAt() ;

	public void setActivatedAt(LocalDateTime activatedAt) ;

}
