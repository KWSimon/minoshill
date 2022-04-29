package testapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration
public class SecurityTest {
	@Autowired
	MockMvc mockMvc;

	@Test
	void publicPageAccessThenXmlSecurity() throws Exception {
		this.mockMvc.perform(get("/page/page.html"))
				.andExpect(isXmlSecurity());
	}

	@Test
	void securePageWhenAnonymousThenJavaSecurity() throws Exception {
		this.mockMvc.perform(get("/secured/secured.html"))
				.andExpect(isJavaSecurity());
	}

	private static ResultMatcher isXmlSecurity() {
		return content().string("hello");
	}

	private static ResultMatcher isJavaSecurity() {
		return content().string("Custom Java configured filter reached");
	}

	@Configuration
	@ImportResource("file:src/main/webapp/WEB-INF/SpringConfig.xml")
	static class TestConfiguration {
		@Bean
		MockMvc mockMvc(WebApplicationContext wac) {
			return MockMvcBuilders.webAppContextSetup(wac)
					.apply(springSecurity())
					.build();
		}

		@RestController
		static class HelloController {
			@RequestMapping("/**")
			String hello() {
				return "hello";
			}
		}
	}
}
