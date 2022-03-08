package de.nuss.demo.pactio.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("provider")
@PactBroker
class ContractVerificationTest {

  @State("findById")
  public void findById()  {
//    reset(productClient);
//    ProductBuilder product = new ProductBuilder()
//        .withProductCode("X00001");
//    when(productClient.find((Set<String>) argThat(contains("X00001")), any())).thenReturn(product);
  }

  @State("findByNonExistingId")
  public void findByNonExistingId()  {

  }

  @State("status")
  public void status()  {

  }

  @State("findAll")
  void findAll()  {
    //    reset(productClient);
    //    ProductBuilder product = new ProductBuilder()
    //        .withProductCode("X00001");
    //    when(productClient.find((Set<String>) argThat(contains("X00001")), any())).thenReturn(product);
  }

  @TestTemplate
  @ExtendWith(PactVerificationSpringProvider.class)
  void pactVerificationTestTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }
}