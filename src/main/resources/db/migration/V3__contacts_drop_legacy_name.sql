-- Alte Fullname-Spalte endgültig entfernen
ALTER TABLE contacts
DROP COLUMN IF EXISTS name;
