package de.ju.wahlkampfmanager.invitations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRequest {
    private String orgName;
    private LocalDate meetingDate;   // oder String, wenn du es lieber als Text willst
    private String meetingTime;      // "HH:mm"
    private String locationName;
    private String locationStreet;
    private String letterCity;
    private String locationCity;
    private List<String> agenda;

    public String getPlace() {
        StringBuilder sb = new StringBuilder();
        if (locationName != null && !locationName.isBlank()) {
            sb.append(locationName);
        }
        if (locationStreet != null && !locationStreet.isBlank()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(locationStreet);
        }
        if (locationCity != null && !locationCity.isBlank()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(locationCity);
        }
        return sb.toString();

    }
    public String getLetterCity() { return letterCity; }
    public void setLetterCity(String letterCity) { this.letterCity = letterCity; }
}


