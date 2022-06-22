package com.agendadigital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mensajesValidacion.properties")
public class MensajeConfig {
}
