package de.nuss.demo.pactio.provider;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AddressbookRestController {

  private Map<String, AddressBookEntry> addressbookEntries;

  public AddressbookRestController() {
    addressbookEntries = new HashMap<>();

    addressbookEntries.put("1", new AddressBookEntry("1", "Max Mustermann"));
    addressbookEntries.put("2", new AddressBookEntry("2", "Peter Musterfrau"));
  }


  @GetMapping("/api/entries")
  public ResponseEntity<Collection<AddressBookEntry>> getAll() {
    return ResponseEntity.ok(addressbookEntries.values());
  }

  @GetMapping("/api/entries/{id}")
  public ResponseEntity<AddressBookEntry> getById(@PathVariable String id) {
    log.info("find by id {}", id);
    if (addressbookEntries.containsKey(id)) {
      return ResponseEntity.ok(addressbookEntries.get(id));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @Value
  public static class AddressBookEntry {

    String id;

    String name;
  }
}
