package com.logging.sample.aspects;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Aspect
public class LoggingHandler {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controller() {
	}
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    private void postMappingPointcut() {
    }
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void getMappingPointcut() {
    }
	
	@Around("postMappingPointcut()")
    public Object logPostAction(final ProceedingJoinPoint joinPoint) throws Throwable {
        return logServiceAction(joinPoint);
    }
	
	@Around("getMappingPointcut()")
    public Object logGetAction(final ProceedingJoinPoint joinPoint) throws Throwable {
        return logServiceAction(joinPoint);
    }

	private Object logServiceAction(ProceedingJoinPoint joinPoint) {
		long start = System.currentTimeMillis();
		try {
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			log.debug("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
			Object result = joinPoint.proceed();
			long elapsedTime = System.currentTimeMillis() - start;
			log.debug("Method " + className + "." + methodName + " ()" + " execution time : "
					+ elapsedTime + " ms");
			log.debug("Response:" + objectMapper.writeValueAsString(result));
			return result;
		} catch (Throwable e) {
			log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
					+ joinPoint.getSignature().getName() + "()");
		}
		return null;
	}
	
	
}
