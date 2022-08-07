package jumba.delivery.service.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserContext {

private String userName;

private String applicationName;

private String ipAddress;

private UUID id;

private String device;

private Long sucursalId;

}
