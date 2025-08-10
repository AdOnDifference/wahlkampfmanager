package de.ju.wahlkampfmanager.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "contacts")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // NEU: Vor- und Nachname in eigenen Spalten
  @NotBlank
  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

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

  // -------------------------------
  // Abwärtskompatibilität: "name"
  // -------------------------------
  // Wird im JSON weiterhin als Feld "name" ausgegeben,
  // ist aber virtuell (keine DB-Spalte).
  @JsonProperty("name")
  @Transient
  public String getName() {
    String fn = firstName == null ? "" : firstName.trim();
    String ln = lastName  == null ? "" : lastName.trim();
    return (fn + " " + ln).trim().replaceAll("\\s+", " ");
  }

  // Akzeptiert weiterhin eingehendes "name" (z. B. altes Frontend)
  @JsonProperty("name")
  public void setName(String full) {
    if (full == null) return;
    String t = full.trim().replaceAll("\\s+", " ");
    int i = t.indexOf(' ');
    if (i < 0) { this.firstName = t; this.lastName = null; }
    else { this.firstName = t.substring(0, i); this.lastName = t.substring(i + 1); }
  }

  // --------
  // Getter/Setter
  // --------
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

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
