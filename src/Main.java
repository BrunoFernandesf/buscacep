import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        List<Endereco> lista = new ArrayList<>();
        int cont = 0;

        while(cont < 3){
            System.out.println("Consulta CEP");
            System.out.println("Digite o CEP que deseja buscar");
            String busca = input.next();

            String endereco = "https://viacep.com.br/ws/"+busca+"/json/";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new Gson();
            Endereco enderecoObj = gson.fromJson(json, Endereco.class);


            lista.add(enderecoObj);
            System.out.println(lista);

            FileWriter escrita = new FileWriter("enderecos.json");
            escrita.write(gson.toJson(lista));
            escrita.close();

            cont++;
        }

    }
}