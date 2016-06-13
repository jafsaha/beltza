package beltza.commons;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class LoggingAspect implements InvocationHandler {

	private static Logger logger = Logger.getLogger(LoggingAspect.class);
	
	protected Object target;
	protected String targetId;
	protected int threshold;

	public LoggingAspect(Object target, String targetId, int threshold) {
		this.target = target;
		this.targetId = targetId;
		this.threshold = threshold;
	}

	public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
		long start = System.currentTimeMillis();
		Object o = method.invoke(target, parameters);
		long end = System.currentTimeMillis();

		long time = end - start;
		if (time >= threshold) {
			String operation = targetId + ";" + method.getName();
			String msg = time + ";"  + operation ;
			logger.debug(msg);
		}
		return o;
	}
	
	
}
