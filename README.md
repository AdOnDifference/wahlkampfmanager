# Wahlkampfmanager – Starter (Spring Boot + Neon Postgres)

Dies ist ein minimales, lauffähiges Backend mit:
- Spring Boot 3
- REST-API `/api/contacts`
- Flyway Migration (legt Tabelle `contacts` an)
- Verbindung zu **Neon Postgres** über Environment-Variablen

## Voraussetzungen
- Java 21
- IntelliJ IDEA (Community/Ultimate)
- Internetzugang (damit Maven Dependencies lädt)
- Deine Neon-Zugangsdaten (JDBC-URL, User, Passwort)

## Start in IntelliJ (mit Neon)
1. **Öffnen:** `File → Open` und den Ordner dieses Projekts wählen.
2. **JDK prüfen:** Rechts unten ggf. "Project SDK" auf **21** setzen.
3. **Run Configuration anlegen:**
   - `Add New Configuration… → Application`
   - **Main class:** `de.ju.wahlkampfmanager.WahlkampfmanagerApplication`
   - **Environment variables:**
     ```
     SPRING_PROFILES_ACTIVE=prod
     DB_URL=jdbc:postgresql://<HOST>/<DB>?sslmode=require
     DB_USER=<USER>
     DB_PASS=<PASSWORT>
     ```
     *Beispiel:* `jdbc:postgresql://ep-xxx-pooler.eu-central-1.aws.neon.tech/neondb?sslmode=require`
4. **Run** – im Log solltest du sehen:
   - `Flyway ... Successfully applied 1 migration to schema "public"`  
   - `Started WahlkampfmanagerApplication`

## Test
```
# Kontakt anlegen
curl -X POST http://localhost:8080/api/contacts  -H "Content-Type: application/json"  -d '{"name":"Max Mustermann","role":"Presse","city":"Flörsheim","email":"max@example.org","consent":true}'

# Liste abrufen
curl http://localhost:8080/api/contacts
```

Du siehst die Tabelle & Daten auch im Neon SQL Editor (linkes Menü → **SQL Editor** → `SELECT * FROM contacts;`).

## GitHub
```
git init
git add .
git commit -m "Starter: Spring Boot + Neon + Contacts API"
git branch -M main
git remote add origin https://github.com/<DEIN-USER>/wahlkampfmanager.git
git push -u origin main
```

Viel Erfolg!