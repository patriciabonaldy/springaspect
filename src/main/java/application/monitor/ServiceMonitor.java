package application.monitor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import application.model.Greeting;

@Aspect
@Component
public class ServiceMonitor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before("execution(* application.controller.*.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		System.out.println("Completed: " + joinPoint);
	}
	
	@Around("execution(* application.controller.GreetingController.greeting (java.lang.String)) && args(name)")
    public Object aroundGreeting(ProceedingJoinPoint proceedingJoinPoint,String name) throws Throwable {
		logger.info("A request was issued for a sample name: "+name);
		name = name+"!";
		Greeting greeting = (Greeting) proceedingJoinPoint.proceed(new Object[] {name});
		//greeting.setName(greeting.getContent().toUpperCase());
		logger.info(""+proceedingJoinPoint);
        return greeting;
    }

}
