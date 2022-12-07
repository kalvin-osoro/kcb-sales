package com.ekenya.rnd.backend.fskcb;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.StatusPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;
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
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.FileHandler;

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
// for Jackson version 1.X
		//mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
// for Jackson version 2.X
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
//

	public static void main(String[] args) {
		//
		prepareLogger(args);
		//
		SpringApplication.run(SpringBootKcbRestApiApplication.class, args);

	}

	@PostConstruct
	public void init(){
		//
		//prepareLogger(null);
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


	public static void prepareLogger(String[] args) {

		try {
//			SLF4JBridgeHandler.removeHandlersForRootLogger();
//			SLF4JBridgeHandler.install();

			//Default: Log inside container, ./logs/xxx
			Path path = Paths.get("logs/kcb-sales-backend");
			Files.createDirectories(path);
			//
			LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
			context.stop();

			String LOG_DIR = path.toAbsolutePath().toString();
			if(args != null && Arrays.stream(args).anyMatch(l-> l.toLowerCase(Locale.ROOT).contains("logs-dir"))){
				String argValue = Arrays.stream(args).filter(l-> l.toLowerCase(Locale.ROOT).contains("logs-dir")).findFirst().get();
				if(argValue.contains("=")){
					LOG_DIR = argValue.split("=")[1].trim();
				}else if(argValue.contains(" ")){
					LOG_DIR = argValue.split(" ")[1].trim();
				}

			}


			PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
			logEncoder.setContext(context);
			logEncoder.setPattern("%-12date{YYYY-MM-dd HH:mm:ss.SSS} %-5level - %msg%n");
			logEncoder.start();

			ConsoleAppender logConsoleAppender = new ConsoleAppender();
			logConsoleAppender.setContext(context);
			logConsoleAppender.setName("Console");
			logConsoleAppender.setEncoder(logEncoder);
			logConsoleAppender.start();


			RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
			rollingFileAppender.setName("APP-FILE-LOGGER");
			rollingFileAppender.setAppend(true);
			rollingFileAppender.setContext(context);

			// OPTIONAL: Set an active log file (separate from the rollover files).
			// If rollingPolicy.fileNamePattern already set, you don't need this.
			//rollingFileAppender.setFile(LOG_DIR + "/logback-app.log");

			ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();
			policy.setMaxFileSize(new FileSize(1048576L * 5));//5MBs
			policy.setFileNamePattern(LOG_DIR+"/logback-app.%d{yyyy-MM-dd HH}.%i.log");
			policy.setTotalSizeCap(new FileSize(1073741824L * 1));//1GB
			policy.setMaxHistory(60);
			policy.setParent(rollingFileAppender);
			policy.setContext(context);
			policy.start();

//		TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
//		rollingPolicy.setFileNamePattern(LOG_DIR + "/log.%d.txt");
//		rollingPolicy.setMaxHistory(7);
//		rollingPolicy.setParent(rollingFileAppender);  // parent and context required!
//		rollingPolicy.setContext(context);
//		rollingPolicy.start();

			rollingFileAppender.setRollingPolicy(policy);

			//Filter out anything < DEBUG
			ThresholdFilter debugFilter = new ThresholdFilter();
			debugFilter.setLevel("DEBUG");
			debugFilter.setContext(context);
			debugFilter.start();
			rollingFileAppender.addFilter(debugFilter);

			PatternLayoutEncoder encoder = new PatternLayoutEncoder();
			encoder.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n ");;
			encoder.setContext(context);
			encoder.start();

			rollingFileAppender.setEncoder(encoder);
			rollingFileAppender.start();

			// add the newly created appenders to the root logger;
			// qualify Logger to disambiguate from org.slf4j.Logger
			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
			root.setLevel(Level.TRACE);
			root.addAppender(rollingFileAppender);
			root.setAdditive(false); /* set to true if root should log too */

			// print any status messages (warnings, etc) encountered in logback config
			//StatusPrinter.print(context);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	private void setupLogbackAppender(Path filePath){
//Install the JUL Bridge
//		SLF4JBridgeHandler.removeHandlersForRootLogger();
//		SLF4JBridgeHandler.install();

		final String LOG_DIR = filePath.toAbsolutePath().toString();

//Obtain an instance of LoggerContext
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.stop();

		PatternLayoutEncoder logEncoder = new PatternLayoutEncoder();
		logEncoder.setContext(context);
		logEncoder.setPattern("%-12date{YYYY-MM-dd HH:mm:ss.SSS} %-5level - %msg%n");
		logEncoder.start();

		ConsoleAppender logConsoleAppender = new ConsoleAppender();
		logConsoleAppender.setContext(context);
		logConsoleAppender.setName("console");
		logConsoleAppender.setEncoder(logEncoder);
		logConsoleAppender.start();



//Create a new FileAppender
		ch.qos.logback.core.rolling.RollingFileAppender<ILoggingEvent> fileAppender = new ch.qos.logback.core.rolling.RollingFileAppender<ILoggingEvent>();
		//fileAppender.setName("APP-FILE-LOGGER");
		fileAppender.setFile(LOG_DIR+"/logback-app.log");
		fileAppender.setContext(context);
		fileAppender.setAppend(true);


		//FileNamePattern pattern = new FileNamePattern("%d{yyyy-MM-dd}.log",context);

//Create a new RollingFileAppender policy
		ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();
		policy.setMaxFileSize(new FileSize(1048576L * 5));//5MBs
		policy.setFileNamePattern(LOG_DIR+"/logback-app.%d{yyyy-MM-dd HH}.%i.log");
		policy.setTotalSizeCap(new FileSize(1073741824L * 1));//1GB
		policy.setMaxHistory(60);
		policy.setParent(fileAppender);
		policy.setContext(context);
		policy.start();


//Filter out anything < WARN
//		ThresholdFilter warningFilter = new ThresholdFilter();
//		warningFilter.setLevel("WARN");
//		warningFilter.setContext(context);
//		warningFilter.start();
//		fileAppender.addFilter(warningFilter);

//Filter out anything < DEBUG
		ThresholdFilter debugFilter = new ThresholdFilter();
		debugFilter.setLevel("DEBUG");
		debugFilter.setContext(context);
		debugFilter.start();
		fileAppender.addFilter(debugFilter);

//Message Encoder
		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		ple.setContext(context);
		ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n ");
		ple.start();
		fileAppender.setEncoder(ple);

		//
		fileAppender.start();

//Get ROOT logger, and add appender to it
		ch.qos.logback.classic.Logger root = context.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.DEBUG);
		root.addAppender(fileAppender);
		root.setAdditive(false); /* set to true if root should log too */

		StatusPrinter.print(context);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}



