//package book.config;
//
//
///*******************************************************************************
// * Copyright 2015 Brient Oh @ Pristine Core
// * boh@pristinecore.com
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *******************************************************************************/
//
//import javax.servlet.Servlet;
//
//import org.h2.server.web.WebServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//@Configuration
//@EnableWebMvc
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}
//	
//	
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/signin").setViewName("signin");
//		registry.addViewController("/error/404.html").setViewName("404");
//		registry.addViewController("/error/505.html").setViewName("505");
//	}
//	
////	@Bean
////	public EmbeddedServletContainerCustomizer containerCustomizer() {
////		return new EmbeddedServletContainerCustomizer() {
////			@Override
////			public void customize(ConfigurableEmbeddedServletContainer container) {
////				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
////				ErrorPage error505Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/505.html");
////				
////				container.addErrorPages(error404Page, error505Page);
////			}
////		};
////	}
//	
//	@Bean
//	ServletRegistrationBean<Servlet> h2servletRegistration(){
//		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
//		registrationBean.addUrlMappings("/console/*");
//		return registrationBean;
//	}
//}
