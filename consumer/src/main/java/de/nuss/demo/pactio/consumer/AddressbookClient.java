package de.nuss.demo.pactio.consumer;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Slf4j
public class AddressbookClient {

  private WebClient webClient;

  public AddressbookClient(String url) {
    this.webClient = WebClient.builder().baseUrl(url).defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
  }

  public List<AddressBookEntry> findAll() {
    return webClient.get().uri("/api/entries").retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<AddressBookEntry>>() {
        }).block();
  }

  public AddressBookEntry findById(String id) {
//    return webClient.get().uri("/api/entries/{id}", id).accept(MediaType.APPLICATION_JSON).retrieve()
//        .bodyToMono(AddressBookEntry.class).block();

    String result = webClient.get().uri("/api/entries/{id}", id).accept(MediaType.APPLICATION_JSON).retrieve()
        .bodyToMono(String.class).block();

    log.info("resutl: {}", result);

    return new AddressBookEntry("1", "sample");
  }

  @Value
  public static class AddressBookEntry {

    String id;

    String name;
  }
}
