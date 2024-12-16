package jumba.delivery.service.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class SecurityContextHolder {
	
public static UserContext getUserContext() {
	UserContext userContext=new UserContext();
	userContext.setUserName("Jumba");
	userContext.setId(UUID.randomUUID());
	return userContext;
}

}
