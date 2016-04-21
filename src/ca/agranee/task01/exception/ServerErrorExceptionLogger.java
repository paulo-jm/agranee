package ca.agranee.task01.exception;

import org.apache.http.ExceptionLogger;

public class ServerErrorExceptionLogger implements ExceptionLogger {

	@Override
	public void log(Exception ex) {
		System.out.println(ex.getMessage());
	}

}
