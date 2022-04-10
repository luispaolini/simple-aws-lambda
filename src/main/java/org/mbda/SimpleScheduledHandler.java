package org.mbda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class SimpleScheduledHandler implements RequestHandler<Void, Void> {

    @Override
    public Void handleRequest(Void unused, Context context) {

        System.out.println("Checking the availability of https://github.com/luispaolini");

        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

        try {
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("https://github.com/luispaolini"))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            HttpResponse<String> result = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (result.statusCode() != 200) {
                // inform the user that the website is down
                System.out.println("The website is down");
            } else {
                System.out.println("The website is up");
            }


        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }
}
