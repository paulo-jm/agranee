package ca.agranee.task01.handler;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

public class FormHandler implements HttpRequestHandler {


    @Override
    public void handle(HttpRequest request, HttpResponse response,
            HttpContext context) throws HttpException, IOException {

        String target = request.getRequestLine().getUri() + "/index.html";

        response.setStatusCode(HttpStatus.SC_OK);
        StringEntity stringEntity = new StringEntity(crateResponseString(),
                ContentType.create("text/html", "UTF-8"));
        response.setEntity(stringEntity);

    }

    private String crateResponseString() {

        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("	<head>");
        sb.append("		<title> Task 01 - Agranee Interview Questions</title>");
        sb.append("     </head>");
        sb.append("	<body>");
        sb.append("         <form method=\"post\" action=\"/task01/print\">");
	sb.append("		<input type=\"text\" name=\"value1\" value=\"\" />");	
	sb.append("		<input  type=\"text\"  name=\"value2\"  value=\"\" />");	
	sb.append("		<input type=\"submit\" value=\"Send\">");
	sb.append("         </form>");                
        sb.append("	</body>");
        sb.append("</html>");

        return sb.toString();

    }

}
