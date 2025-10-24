package ex4;

import java.net.http.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SentimentoAzure {
    public static void main(String[] args) throws Exception {
        String endpoint = System.getenv("AZURE_AI_ENDPOINT"); // https://analisador-texto-miro.cognitiveservices.azure.com
        String key = System.getenv("AZURE_AI_KEY");

        String url = endpoint + "/language/:analyze-text?api-version=2023-04-01";

        String payload = """
        {
          "kind": "SentimentAnalysis",
          "analysisInput": {
            "documents": [
              { "id": "1", "language": "pt-BR", "text": "Adoro estudar e aprender coisas novas!" },
              { "id": "2", "language": "pt-BR", "text": "O atendimento foi péssimo e demorado." }
            ]
          },
          "parameters": {
            "modelVersion": "latest",
            "opinionMining": true
          }
        }
        """;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Ocp-Apim-Subscription-Key", key)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("HTTP " + response.statusCode());
        System.out.println(response.body());
    }
}
