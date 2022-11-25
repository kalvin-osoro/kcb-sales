package com.ekenya.rnd.backend.fskcb;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.rolling.helper.FileNamePattern;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.StatusPrinter;
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

	private FileHandler mFileHandler;

	public static void main(String[] args) {
		//
		new File(FileStorageService.uploadPath).mkdir();
		//
		SpringApplication.run(SpringBootKcbRestApiApplication.class, args);

	}

	@PostConstruct
	public void init(){
		//
		prepareLogger();
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
	public FileHandler getLogsFileHandler() {
		//
		if(mFileHandler == null){
			prepareLogger();
		}
		return mFileHandler;
	}

	public FileHandler prepareLogger() {

		try {
			//Install the JUL Bridge

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			Path path = Paths.get("logs/kcb-sales-backend");
			Files.createDirectories(path);
			// This block configure the logger with handler and formatter
			mFileHandler = new FileHandler("logs/kcb-sales-backend/kcb-sales-log-file-" + sdf.format(Calendar.getInstance().getTime()) + ".log");
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

	private void setupLogbackAppender1(Path path){
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.stop();

		final String LOG_DIR = path.toAbsolutePath().toString();


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
		rollingFileAppender.setAppend(true);
		rollingFileAppender.setContext(context);

		// OPTIONAL: Set an active log file (separate from the rollover files).
		// If rollingPolicy.fileNamePattern already set, you don't need this.
		rollingFileAppender.setFile(LOG_DIR + "/logback-app.log");

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

		// print any status messages (warnings, etc) encountered in logback config
		StatusPrinter.print(context);
	}
}



