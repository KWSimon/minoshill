package testapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.FilterChainProxy;

import javax.servlet.Filter;

@Configuration
public class AggregateSpringSecurityConfiguration {
	public static final String AGGREGATE_SPRING_SECURITY_FILTER_CHAIN_ID = "aggregateSpringSecurityFilterChain";

	/**
	 * Provide a new FilterChainProxy that contains both XML and Java Configuration
	 * @param webSecurityConfiguration
	 * @return
	 * @throws Exception
	 */
	@Bean(AGGREGATE_SPRING_SECURITY_FILTER_CHAIN_ID)
	Filter aggregateSpringSecurityFilterChain(WebSecurityConfiguration webSecurityConfiguration) throws Exception {
		FilterChainProxy javaConfigFcp = (FilterChainProxy) webSecurityConfiguration.springSecurityFilterChain();
		return new FilterChainProxy(javaConfigFcp.getFilterChains());
	}

}
