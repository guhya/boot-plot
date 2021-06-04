package net.guhya.boot.plot.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.guhya.boot.plot.common.web.request.CustomArgumentResolver;

@Component
public class ArgumentResolver implements WebMvcConfigurer {

	@Autowired
	CustomArgumentResolver customArgumentResolver;
	
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(customArgumentResolver);
	}
}
