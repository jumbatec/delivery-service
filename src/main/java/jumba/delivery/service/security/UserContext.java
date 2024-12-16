package jumba.delivery.service.security;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContext {

private String userName;

private String applicationName;

private String ipAddress;

private UUID id;

private String device;

private Long sucursalId;

public static UserContext getDefaulUserContext(){
    return UserContext.builder().userName("admin").build();
}

}
