package jumba.delivery.service.generic.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class LifeCycleEntity<T>  implements ILifeCycleEntity<T> {
	
	@Column(name="id",nullable=false)
	@Id
	private T id;

	@Column(name="created_by",nullable=false)
	private UUID createdBy;
	
	@Column(name="updated_by")
	private UUID updatedBy;
	
	@Column(name="activated_by",nullable=false)
	private UUID activatedBy;
	
	@Column(name="state",nullable=false)
	private int state;
	
	@Column(name="active",nullable=false)
	private boolean active;
	
	@Column(name="created_at",nullable=false)
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="activated_at",nullable=false)
	private LocalDateTime activatedAt;
	
	@Column(name="sucursal_id")
	private Long sucursalId;

	@PrePersist
	void createdAt() {
		this.createdAt=LocalDateTime.now();
		this.activatedAt=LocalDateTime.now();
	}
}
