package de.ju.wahlkampfmanager.contacts;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin
public class ContactController {

  private final ContactRepository repo;

  public ContactController(ContactRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Contact> list() { return repo.findAll(); }

  @GetMapping("/{id}")
  public Contact get(@PathVariable Long id) {
    return repo.findById(id).orElseThrow(() -> new RuntimeException("Contact " + id + " not found"));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Contact create(@Valid @RequestBody Contact c) { return repo.save(c); }

  @PutMapping("/{id}")
  public Contact update(@PathVariable Long id, @Valid @RequestBody Contact c) {
    Contact existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Contact " + id + " not found"));
    c.setId(existing.getId());
    return repo.save(c);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
