package jumba.delivery.service.aop;

import jumba.delivery.service.annotations.Auditable;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class AuditingInterceptor {

    @Before("@annotation(auditable)")
    public void auditOperation(Auditable auditable){
        String auditCode = auditable.value();
    }

}
