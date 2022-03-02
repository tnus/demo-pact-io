package de.nuss.demo.pactio.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.Request;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "provider")
@Slf4j
class AddressbookClientTest {

  @Pact(provider="provider", consumer="consumer")
  public V4Pact findByIdPact(PactDslWithProvider builder) {
    return builder
        .given("findById")
        .uponReceiving("Existing id")
        .path("/api/entries/1")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body("""
            {"id":"1","name":"Max Mustermann"}
            """, "application/json")
        .toPact(V4Pact.class);
  }

//  @Pact(provider="provider", consumer="consumer")
//  public V4Pact findAllPact(PactDslWithProvider builder) {
//    return builder
//        .given("findAll")
//        .uponReceiving("Find all")
//        .path("/api/entries")
//        .method("GET")
//        .willRespondWith()
//        .status(200)
//        .body("""
//            [{"id":"1","name":"Max Mustermann"},{"id":"2","name":"Peter Musterfrau"}]
//            """, "application/json")
//        .toPact(V4Pact.class);
//  }

  @Test
  void findById(MockServer mockServer) throws IOException {
    log.info("server is running on {} and port {}", mockServer.getUrl(), mockServer.getPort());

    AddressbookClient.AddressBookEntry addressBookEntry = new AddressbookClient("http://localhost:" + mockServer.getPort()).findById("1");
    assertNotNull(addressBookEntry);
  }

  @Test
  void findByAll(MockServer mockServer) throws IOException {
    log.info("server is running on {} and port {}", mockServer.getUrl(), mockServer.getPort());

    List<AddressbookClient.AddressBookEntry> addressBookEntries = new AddressbookClient(
        "http://localhost:" + mockServer.getPort()).findAll();
    assertNotNull(addressBookEntries);
  }
}