package ca.agranee.task01.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;

import com.agranee.utils.QueryMapUtils;

public class PrintHandler implements HttpRequestHandler {

    public void handle(HttpRequest request, HttpResponse response,
            HttpContext context) throws HttpException, IOException {

        HttpEntity entity = null;
        if (request instanceof HttpEntityEnclosingRequest) {
            entity = ((HttpEntityEnclosingRequest) request).getEntity();
        }

        String data;
        Map<String, String> map = null;
        if (entity == null) {
            data = "";
        } else {
            data = EntityUtils.toString(entity);
            map = QueryMapUtils.getAsMap(data);
        }

        response.setStatusCode(HttpStatus.SC_OK);
        StringEntity stringEntity = new StringEntity(crateResponseString(map),
                ContentType.create("text/html", "UTF-8"));
        response.setEntity(stringEntity);

    }

    private String crateResponseString(Map<String, String> map) {

        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("		<head>");
        sb.append("			<title> Task 01 - Agranee Interview Questions</title>");
        sb.append("		</head>");

        sb.append("		<body>");
        sb.append("			<ul>");
        for (Entry<String, String> entry : map.entrySet()) {

            String toAppend = String.format("<li> <strong>%s</strong> : %s </li>", entry.getKey(), entry.getValue());
            sb.append(toAppend);

        }
        sb.append("			</ul>");
        sb.append("		</body>");
        sb.append("</html>");

        return sb.toString();

    }

}
