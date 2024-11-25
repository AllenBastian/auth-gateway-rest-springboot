package com.learn.auth.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.auth.configuration.principal.AdminUser;
import com.learn.auth.configuration.principal.NormalUser;
import com.learn.auth.json.Mixin;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig implements BeanClassLoaderAware {

    private ClassLoader loader;

    @Bean
    public LettuceConnectionFactory connectionFactory(){
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper());
    }



    //required to map security modules such as context into redis
    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(AdminUser.class, Mixin.class);
        mapper.addMixIn(NormalUser.class, Mixin.class);
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
        return mapper;
    }

    //dynamically loads the class
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

}


