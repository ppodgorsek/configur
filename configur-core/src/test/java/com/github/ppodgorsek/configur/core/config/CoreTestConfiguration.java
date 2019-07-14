package com.github.ppodgorsek.configur.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Test configuration for the core module.
 *
 * @author Paul Podgorsek
 */
@Configuration
@Import({ CoreListenerTestConfiguration.class, CoreServiceTestConfiguration.class })
public class CoreTestConfiguration {

}
