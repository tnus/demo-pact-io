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
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "provider")
@Slf4j
class AddressbookClientTest {

  @Pact(provider = "provider", consumer = "consumer")
  public V4Pact findByExistingIdPact(PactDslWithProvider builder) {
    return builder.given("findById").uponReceiving("Existing id").path("/api/entries/1").method("GET").willRespondWith()
        .status(200).body("""
            {"id":"1","name":"Max Mustermann"}
            """, "application/json").toPact().asV4Pact().component1();
  }

  @Test
  @PactTestFor(pactMethod = "findByExistingIdPact")
  void findByExistingIdTest(MockServer mockServer) throws IOException {
    log.info("server is running on {} and port {}", mockServer.getUrl(), mockServer.getPort());

    AddressbookClient.AddressBookEntry addressBookEntry = new AddressbookClient(
        "http://localhost:" + mockServer.getPort()).findById("1");
    assertNotNull(addressBookEntry);
  }

  @Pact(provider = "provider", consumer = "consumer")
  public V4Pact findByNonExistingIdPact(PactDslWithProvider builder) {
    return builder.given("findByNonExistingId")
        .uponReceiving("When the id does not exist, an 404 status should be returned.")
        .path("/api/entries/non-existing").method("GET").willRespondWith().status(404).toPact().asV4Pact().component1();
  }

  @Test
  @PactTestFor(pactMethod = "findByNonExistingIdPact")
  void findByNonExistingIdTest(MockServer mockServer) throws IOException {
    log.info("server is running on {} and port {}", mockServer.getUrl(), mockServer.getPort());

    AddressbookClient.AddressBookEntry addressBookEntry = new AddressbookClient(
        "http://localhost:" + mockServer.getPort()).findById("non-existing");
    assertNull(addressBookEntry);

  }

  @Pact(provider = "provider", consumer = "consumer")
  public V4Pact findAllPact(PactDslWithProvider builder) {
    return builder.given("findAll").uponReceiving("Find all").path("/api/entries").method("GET").willRespondWith()
        .status(200).body("""
            [{"id":"1","name":"Max Mustermann"},{"id":"2","name":"Peter Musterfrau"}]
            """, "application/json").toPact().asV4Pact().component1();
  }

  @Test
  @PactTestFor(pactMethod = "findAllPact")
  void findAllTest(MockServer mockServer) throws IOException {
    log.info("server is running on {} and port {}", mockServer.getUrl(), mockServer.getPort());

    List<AddressbookClient.AddressBookEntry> addressBookEntries = new AddressbookClient(
        "http://localhost:" + mockServer.getPort()).findAll();
    assertNotNull(addressBookEntries);
  }

}