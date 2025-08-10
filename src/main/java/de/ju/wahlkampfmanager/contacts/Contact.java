package de.ju.wahlkampfmanager.contacts;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "contacts")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  private String role;
  private String city;
  private String email;
  private String phone;
  private String tags;
  private Boolean consent = false;
  private LocalDate consentDate;
  private LocalDate lastTouch;
  private LocalDate nextFollowup;

  public Contact() {}

  // getters and setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }

  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getTags() { return tags; }
  public void setTags(String tags) { this.tags = tags; }

  public Boolean getConsent() { return consent; }
  public void setConsent(Boolean consent) { this.consent = consent; }

  public LocalDate getConsentDate() { return consentDate; }
  public void setConsentDate(LocalDate consentDate) { this.consentDate = consentDate; }

  public LocalDate getLastTouch() { return lastTouch; }
  public void setLastTouch(LocalDate lastTouch) { this.lastTouch = lastTouch; }

  public LocalDate getNextFollowup() { return nextFollowup; }
  public void setNextFollowup(LocalDate nextFollowup) { this.nextFollowup = nextFollowup; }
}
