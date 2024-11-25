# Basic Microservice Setup with Centralized Session Management using Spring Session and Redis

## Overview

- **All calls** are routed to different services through the **Gateway**.
- **Auth-service** has two security configurations for two kinds of users, using two `AuthenticationManager` (DAOauthenticationManager) with two `UserDetailsService` .
- Session stored by **Auth Service** can be accessed by different services.
- **Each user** can access only the appropriate services.

---

## Limitations and Issues with the Current Setup

### Problem:
Currently, **even though** `UserDetails` has a custom class implementing it, before saving the context into the session, a **new `User` Object** (`org.springframework.security.core.userdetails.User`) is instantiated with the principal in order for other services to deserialize it properly.

Although this setup works, it **limits** us from having our own custom methods outside of the `User` class (`org.springframework.security.core.userdetails`).

### Attempts and Solutions Tried:

1. **Serialization Configuration** (Using Spring Session and Redis):
    - Followed this section [Spring Session Redis Documentation] about configuring serialization for distributed environments (https://docs.spring.io/spring-session/reference/configuration/redis.html#serializing-session-using-json) - **Not working**.
    
2. **Mixin for Custom User Class**:
    - Added **Mixins** for the `User` class - **Not working**.
    
3. **Shared Class Paths**:
    - Configured the **exact same path** (class names) for `UserDetails` in all services - **This works**, but it leads to duplicated code and inconvenience.

### Example of Session Data Stored in Redis:

#### With Deserialization Error:

  
3) "sessionAttr:SPRING_SECURITY_CONTEXT"
4) "{\"@class\":\"org.springframework.security.core.context.SecurityContextImpl\",\"authentication\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$EmptyList\",[]],\"details\":null,\"authenticated\":true,\"principal\":{\"@class\":\"com.learn.auth.configuration.principal.AdminUser\",\"username\":\"user\",\"password\":\"password\",\"authorities\":null,\"enabled\":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"accountNonLocked\":true},\"credentials\":null}}"



In this case, **the principal serialized** and stored here has the `@class` annotation with the **class path** to that specific class in the **Auth-Service**, causing **other services** to fail when deserializing the session.

---

#### With Current Working Setup:


This is how security context is stored with current setup:
3) "sessionAttr:SPRING_SECURITY_CONTEXT"
4) "{\"@class\":\"org.springframework.security.core.context.SecurityContextImpl\",\"authentication\":{\"@class\":\"org.springframework.security.authentication.UsernamePasswordAuthenticationToken\",\"authorities\":[\"java.util.Collections$UnmodifiableRandomAccessList\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ADMIN\"}]],\"details\":null,\"authenticated\":true,\"principal\":{\"@class\":\"org.springframework.security.core.userdetails.User\",\"password\":\"password\",\"username\":\"user\",\"authorities\":[\"java.util.Collections$UnmodifiableSet\",[{\"@class\":\"org.springframework.security.core.authority.SimpleGrantedAuthority\",\"authority\":\"ADMIN\"}]],\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true},\"credentials\":null}}"

In this setup, even though the `@class` annotation is used, **the class path** corresponds to the common **`User` class** (`org.springframework.security.core.userdetails.User`), which is the default class used by **Spring Security**, so it is deserialized properly and everything works.

## If there is any solution or the correct way to do this please help.
