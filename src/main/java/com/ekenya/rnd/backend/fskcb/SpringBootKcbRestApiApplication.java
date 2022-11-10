package com.ekenya.rnd.backend.fskcb;

import com.ekenya.rnd.backend.fskcb.CrmModule.CrmIntegrations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
@EnableSwagger2
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Slf4j
public class SpringBootKcbRestApiApplication   {
	@Autowired
	private CrmIntegrations crmIntegrations;
	public static String accessToken ;
	@Resource
	public Environment environment;




	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Bean
//	public AuditorAware<String> auditorAware() {
//		return new SpringSecurityAuditorAware();
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKcbRestApiApplication.class, args);

	}

		@PostConstruct
    void processToken(){
        System.out.println(
                "generate token"
        );
        accessToken = crmIntegrations.generateOauth2Token();
    }
//	@Bean
//	CommandLineRunner init(RoleRepository roleRepository) {
//		return args -> {
//			Role userRole = new Role();
//			userRole.setName("ROLE_USER");
//			roleRepository.save(userRole);
//			Role adminRole = new Role();
//			adminRole.setName("ROLE_ADMIN");
//			roleRepository.save(adminRole);
//
//		};
//	}



}



