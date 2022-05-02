package com.redhat.developer.demo.preferences;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentracing.Tracer;
import io.opentracing.noop.NoopTracerFactory;

@Configuration
@ConditionalOnProperty(value = "opentracing.jaeger.enabled", havingValue = "false", matchIfMissing = false)
public class TracerConfig {

    @Bean
    public Tracer jaegerTracer() {
        return NoopTracerFactory.create();
    }
    
}
