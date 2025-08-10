-- Neue Spalten
ALTER TABLE contacts
    ADD COLUMN first_name varchar(100),
  ADD COLUMN last_name  varchar(100);

-- Backfill aus bisherigem "name"
UPDATE contacts
SET
    first_name = CASE
                     WHEN name IS NULL OR name = '' THEN NULL
                     WHEN position(' ' in name) = 0 THEN name
                     ELSE split_part(name,' ',1)
        END,
    last_name  = CASE
                     WHEN name IS NULL OR name = '' THEN NULL
                     WHEN position(' ' in name) = 0 THEN NULL
                     ELSE substring(name from '^\S+\s+(.*)$')
        END;

-- (Optional) Indizes für schnellere Suche
CREATE INDEX IF NOT EXISTS idx_contacts_first_name ON contacts (first_name);
CREATE INDEX IF NOT EXISTS idx_contacts_last_name  ON contacts (last_name);
