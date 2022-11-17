package com.ekenya.rnd.backend.fskcb;

import com.ekenya.rnd.backend.fskcb.CrmAdapter.ICRMService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableSwagger2
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Slf4j
public class SpringBootKcbRestApiApplication   {
	@Autowired
	private ICRMService ICRMService;
	public static String accessToken ;
	@Resource
	public Environment environment;



	@Bean
	public DateTimeFormatter dateFormatter() {
		return DateTimeFormatter.ISO_DATE_TIME;
	}

	//
	@Bean
	public DateFormat simpleDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
	}
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
//
//	@Bean
//	public ExcelService excelService() {
//		return new ExcelService();
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKcbRestApiApplication.class, args);

	}

		@PostConstruct
    void processToken(){
        System.out.println(
                "generate token"
        );
        accessToken = ICRMService.generateOauth2Token();
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



