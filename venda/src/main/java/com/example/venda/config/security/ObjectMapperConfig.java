package com.example.venda.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configurações personalizadas do ObjectMapper
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Saída JSON formatada
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS); // Não falhar em classes sem propriedades
        objectMapper.findAndRegisterModules(); // Suporte para JavaTime (datas)

        return objectMapper;
    }
}
