package io.mzlnk.authdemo.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserModule {

    @Bean
    public UserFacade userFacade() {
        return new UserFacade(new InMemoryUserRepository());
    }

}
