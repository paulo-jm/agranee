package ca.agranee.task01.http;

import ca.agranee.task01.exception.ServerErrorExceptionLogger;
import ca.agranee.task01.handler.FormHandler;
import java.util.concurrent.TimeUnit;

import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;


import ca.agranee.task01.handler.PrintHandler;

public class Server {

	public static void main(String[] args) throws Exception {

		int port = 8080;
		
		if (args.length >= 1) {
			
			port = Integer.parseInt(args[0]);
			
		}
		
		SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(15000)
                .setTcpNoDelay(true)
                .build();

        final HttpServer server = ServerBootstrap.bootstrap()
                .setListenerPort(port)
                .setServerInfo("Agranee Interview Questions/1.1")
                .setSocketConfig(socketConfig)
                .setExceptionLogger(new ServerErrorExceptionLogger())
                .registerHandler("/task01", new FormHandler())
                .registerHandler("/task01/print", new PrintHandler())
                .create();

        server.start();
        server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		
	}

}
