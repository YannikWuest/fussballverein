USE fussballverein;

-- Tabelle erstellen für Entity Adresse
CREATE TABLE IF NOT EXISTS adresse (
  id binary(16) Primary Key,
  plz int(5),
  ort varchar(50),

  INDEX adresse_plz_idx(plz)
);

-- Tabelle erstellen für Entity fusballverein
CREATE TABLE IF NOT EXISTS fussballverein (
  id binary(16) Primary Key,
  version int not null default 0,
  mannschaft int,
  vereinsname varchar(50) not null,
  email varchar(50) unique not null,
  mitglied_id binary(16),
  entstehungsdatum Date,
  homepage varchar(50),
  adresse_id binary(16) not null references adresse,
  erzeugt date not null,
  aktualisiert date,

  INDEX fussballverein_vereinsname_idx(vereinsname)
);
