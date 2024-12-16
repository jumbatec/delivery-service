package jumba.delivery.service.exceptions;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(String message){
        super(message);
    }
}
