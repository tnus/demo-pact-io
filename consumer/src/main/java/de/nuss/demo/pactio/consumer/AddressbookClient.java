package de.nuss.demo.pactio.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    return webClient.get().uri("/api/entries/{id}", id).retrieve().onStatus( httpStatus -> httpStatus.value() == 404 ,response -> Mono.empty())
        .bodyToMono(AddressBookEntry.class).onErrorMap(throwable -> new IllegalStateException("service cannot be called", throwable)).block();
  }

  public String status() {
    return webClient.get().uri("/api/status").accept(MediaType.TEXT_PLAIN).retrieve()
        .bodyToMono(String.class).onErrorReturn("down").block();
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AddressBookEntry {

    String id;

    String name;
  }
}
