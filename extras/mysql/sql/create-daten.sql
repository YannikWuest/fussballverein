USE fussballverein;

-- Tabelle erstellen für Entity Adresse
CREATE TABLE IF NOT EXISTS Adresse (
  AdresseId binary(16) Primary Key,
  Postleitzahl int(5),
  Ort varchar(50)
);

-- Tabelle erstellen für Entity Fusballverein
CREATE TABLE IF NOT EXISTS Fussballverein (
  Id binary(16) Primary Key,
  Vereinsname varchar(50),
  Email varchar(50),
  Entstehungsdatum Date,
  Homepage varchar(50),
  Adresse varchar(36),
  CONSTRAINT fk_adresse FOREIGN KEY (Adresse) REFERENCES Adresse(AdresseId)
);

-- Testdaten für Entity Adresse
INSERT INTO Adresse(AdresseId, Postleitzahl, Ort) VALUES
  (UUID_TO_BIN(00000000-0000-0000-0000-000000000010),76351,'Linkenheim-Hochstetten'),
  (UUID_TO_BIN(00000000-0000-0000-0000-000000000020),10001,'New York'),
  (UUID_TO_BIN(00000000-0000-0000-0000-000000000030),10115,'Berlin');

-- Testdaten für Entity Fussballverein
INSERT INTO Fussballverein(Id, Vereinsname, Email, Entstehungsdatum, Homepage, Adresse) VALUES
  (UUID_TO_BIN(00000000-0000-0000-0000-000000000001),'FV Hochstetten','fvhochstetten@acme.de','1916-01-01',
   'https://www.fv-hochstetten.de',UUID_TO_BIN(00000000-0000-0000-0000-000000000010)),
  (UUID_TO_BIN(00000000-0000-0000-0000-000000000002),'New York City FC','nycfc@acme.com','2013-05-23',
   'https://www.nycfc.com',UUID_TO_BIN(00000000-0000-0000-0000-000000000020)),
  (UUID_TO_BIN(00000000-0000-0000-0000-000000000003),'Hertha BSC','herthabsc@acme.de','1982-07-25',
   'https://www.herthabsc.com', UUID_TO_BIN(00000000-0000-0000-0000-000000000030));
