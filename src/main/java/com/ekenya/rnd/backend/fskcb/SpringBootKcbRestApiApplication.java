package com.ekenya.rnd.backend.fskcb;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import com.ekenya.rnd.backend.fskcb.CrmAdapter.ICRMService;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
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
import java.util.logging.Logger;

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
		new File(FileStorageService.uploadPath).mkdir();
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
			//Install the JUL Bridge

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
			//
			setupLogbackAppender(path);

			return mFileHandler;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}


	private void setupLogbackAppender(Path filePath){
//Install the JUL Bridge
//		SLF4JBridgeHandler.removeHandlersForRootLogger();
//		SLF4JBridgeHandler.install();

//Obtain an instance of LoggerContext
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
		logEncoder.setContext(context);
		logEncoder.setPattern("%-12date{YYYY-MM-dd HH:mm:ss.SSS} %-5level - %msg%n");
		logEncoder.start();

		ConsoleAppender logConsoleAppender = new ConsoleAppender();
		logConsoleAppender.setContext(context);
		logConsoleAppender.setName("console");
		logConsoleAppender.setEncoder(logEncoder);
		logConsoleAppender.start();

//Create a new RollingFileAppender policy
//		ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();
//		policy.setMaxFileSize(new FileSize(1048576L * 5));//5MBs
//		policy.setFileNamePattern("logs/archived/app.%d{yyyy-MM-dd}.log");
//		policy.setTotalSizeCap(new FileSize(1073741824L * 1));//1GB
//		policy.setMaxHistory(60);
//		policy.start();


		ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy policy = new ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy();
		policy.setMaxFileSize(new FileSize(1048576L * 5));//5MBs
		policy.start();


//Create a new FileAppender
//		ch.qos.logback.core.rolling.RollingFileAppender<ILoggingEvent> fileAppender = new ch.qos.logback.core.rolling.RollingFileAppender<ILoggingEvent>();
//		fileAppender.setName("APP-FILE-LOGGER");
//		//fileAppender.setRollingPolicy(policy);
//		fileAppender.setTriggeringPolicy(policy);
//		fileAppender.setFile(filePath.toAbsolutePath().toString()+"/logback-app.log");
//		fileAppender.setContext(context);
//		fileAppender.setAppend(true);


		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		fileAppender.setName("APP-FILE-LOGGER");
		fileAppender.setFile(filePath.toAbsolutePath().toString()+"/logback-app.log");
		fileAppender.setContext(context);
		fileAppender.setAppend(true);

//Filter out anything < WARN
		ThresholdFilter warningFilter = new ThresholdFilter();
		warningFilter.setLevel("WARN");
		warningFilter.setContext(context);
		warningFilter.start();
		fileAppender.addFilter(warningFilter);

//Filter out anything < DEBUG
//		ThresholdFilter debugFilter = new ThresholdFilter();
//		debugFilter.setLevel("DEBUG");
//		debugFilter.setContext(context);
//		debugFilter.start();
//		fileAppender.addFilter(debugFilter);

//Message Encoder
		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		ple.setContext(context);
		ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n ");
		ple.start();
		fileAppender.setEncoder(ple);

		//
		fileAppender.setImmediateFlush(true);
		fileAppender.start();


//Get ROOT logger, and add appender to it
		ch.qos.logback.classic.Logger root = context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.DEBUG);
		root.addAppender(fileAppender);
		root.setAdditive(true); /* set to true if root should log too */


		String f = fileAppender.getFile();
		System.out.println("Appender file => "+fileAppender.getFile());
	}
}



