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
public class StatusRestController {

  @GetMapping("/api/status")
  public ResponseEntity<String> status() {
    return ResponseEntity.ok("up");
  }
}
