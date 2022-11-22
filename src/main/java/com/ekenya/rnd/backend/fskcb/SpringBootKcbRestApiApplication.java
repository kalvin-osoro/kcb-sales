package com.ekenya.rnd.backend.fskcb;

import com.ekenya.rnd.backend.fskcb.CrmAdapter.ICRMService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

@SpringBootApplication
@EnableSwagger2
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Slf4j
public class SpringBootKcbRestApiApplication   {
	@Resource
	public Environment environment;



	@Bean
	public DateTimeFormatter dateFormatter() {
		return DateTimeFormatter.ISO_DATE_TIME;
	}

	//
	@Bean
	public DateFormat simpleDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	}
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		//
		mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		//
		return mapper;
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


	@Bean
	public FileHandler prepareLogger() {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			Path path = Paths.get("logs/kcb-sales-backend");
			Files.createDirectories(path);
			// This block configure the logger with handler and formatter
			FileHandler mFileHandler = new FileHandler("logs/kcb-sales-backend/kcb-sales-log-file-" + sdf.format(Calendar.getInstance().getTime()) + ".log");
			//mLogger.addHandler(mFileHandler);
			// SimpleFormatter formatter = new SimpleFormatter();
			mFileHandler.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord record) {
					SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					Calendar cal = new GregorianCalendar();
					cal.setTimeInMillis(record.getMillis());
					return record.getLevel() + " > " + logTime.format(cal.getTime()) + " || "
							+ record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1,
							record.getSourceClassName().length())
							+ "." + record.getSourceMethodName() + "() : " + record.getMessage() + "\n";
				}
			});
			return mFileHandler;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}



