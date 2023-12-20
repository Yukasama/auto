CREATE TABLE IF NOT EXISTS besitzer (
    id        UUID PRIMARY KEY,
    vorname   VARCHAR(100) NOT NULL,
    nachname  VARCHAR(100) NOT NULL
);
CREATE INDEX IF NOT EXISTS besitzer_nachname_idx ON besitzer(nachname);

CREATE TABLE IF NOT EXISTS auto (
    id             UUID PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    marke          VARCHAR(10) CHECK (marke ~ 'VOLKSWAGEN|MERCEDES|FORD|TESLA'),
    kennzeichen    VARCHAR(10) NOT NULL CHECK (kennzeichen ~ '^[A-ZÄÖÜ]{1,3}-[A-Z]{1,2}-[1-9]\d{0,3}E?'),
    pferde_staerke  INTEGER CHECK (pferde_staerke > 0),
    preis          DECIMAL(10, 2),
    features       VARCHAR(30),
    besitzer_id    UUID NOT NULL UNIQUE REFERENCES besitzer,
    created_at     TIMESTAMP NOT NULL,
    updated_at     TIMESTAMP NOT NULL
);
CREATE INDEX IF NOT EXISTS auto_name_idx ON auto(name);

CREATE TABLE IF NOT EXISTS reparatur (
    id            UUID PRIMARY KEY,
    beschreibung  VARCHAR(200) NOT NULL,
    datum         DATE CHECK(datum < current_date),
    kosten        DECIMAL(10,2) NOT NULL,
    auto_id       UUID REFERENCES auto,
    idx           INTEGER NOT NULL DEFAULT 0
);
CREATE INDEX IF NOT EXISTS reparatur_auto_id_idx ON reparatur(auto_id);
