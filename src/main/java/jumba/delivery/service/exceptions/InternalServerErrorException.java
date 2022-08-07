package jumba.delivery.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class InternalServerErrorException extends Exception{
    private final String message;
    private final String localizedMessage;
    private Object object;

}
