#Basic Microservice Setup, centralized session managemnet using Spring-session and Redis and basic Spring-security Authorization and Authentication Example.

- All calls are routed to different services through the  gateway
- Auth-service has two security configuration for two kinds of users, using two AuthenticationManager with two UserDetailsService which is of the same type (DAOauthManager).
- Session stored by auth service can be accessed at different services
- Each user can access only the approriate services

  Limitations and issue with this setup

   Currently the eventhough UserDetails has a custom class implemeting it, before saving the context into session, new User Object (org.springframework.security.core.userdetails) is instatiated with
   the principal inorder for other services to deserialize it properly. Eventhough this works, this limits us from having our own custom methods other than in User 
   (org.springframework.security.core.userdetails). 

   tried methods-
     - https://docs.spring.io/spring-session/reference/configuration/redis.html#serializing-session-using-json (not working)
     - Added Mixins for the User class (not wroking)
     - Configure exact same path (same classnames) for UserDetails in all services (this works)

  This is how the security context is stored in Redis when deserialization error occurs :
  
3) "sessionAttr:SPRING_SECURITY_CONTEXT"
4) "{\"@class\":\"org.springframework.security.core.context.SecurityContextImpl\",\"authentication\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$EmptyList\",[]],\"details\":null,\"authenticated\":true,\"principal\":{\"@class\":\"com.learn.auth.configuration.principal.AdminUser\",\"username\":\"user\",\"password\":\"password\",\"authorities\":null,\"enabled\":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"accountNonLocked\":true},\"credentials\":null}}"


as we can see the principal serialized and stored here has @class annotation and which also has the class path to that specific class in auth-service causing other services cannoth find this to deserialize.

This is how security context is stored with current setup:
3) "sessionAttr:SPRING_SECURITY_CONTEXT"
4) "{\"@class\":\"org.springframework.security.core.context.SecurityContextImpl\",\"authentication\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ADMIN\"}]],\"details\":null,\"authenticated\":true,\"principal\":{\"@class\":\"org.springframework.security.core.userdetails.User\",\"password\":\"password\",\"username\":\"user\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ADMIN\"}]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true},\"credentials\":null}}"

as we can see eventhough this has @class annotation, the classPath is of the common class of User (org.springframework.security.core.userdetails) which by default spring-secuirty has so it is deserialized properly and working

If there is any solution or the correct way to this please help.
