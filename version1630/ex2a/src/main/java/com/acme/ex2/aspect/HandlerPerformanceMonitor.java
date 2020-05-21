package com.acme.ex2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@Aspect @ManagedResource
public class HandlerPerformanceMonitor {

	@Autowired
	private Logger logger;
	
	private boolean enabled;
	
	@ManagedAttribute
	public boolean isEnabled() {
		return enabled;
	}

	@ManagedAttribute
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Around("execution(* com.acme.ex2.business..*.handle(..))")
	public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
		if(!enabled)return pjp.proceed();
		
		long n = System.currentTimeMillis();
		// appel du joinpoint
		Object ret = pjp.proceed();
		long n2 = System.currentTimeMillis();
		// log de la différence entre n2 et n pour indiquer le temps d'exécution de la méthode.
		// renvoi du résultat renvoyé par le joinpoint
		logger.info("it took {}ms to call {}", (n2-n), pjp.toString());
		return ret;
	}
}
