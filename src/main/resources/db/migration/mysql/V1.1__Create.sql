USE fussballverein;

INSERT INTO adresse (id, plz, ort)
VALUES
  (UUID_TO_BIN('20000000-0000-0000-0000-000000000000'),10001,'LigaStadt'),
  (UUID_TO_BIN('20000000-0000-0000-0000-000000000001'),76351,'LinkenheimHochtetten'),
  (UUID_TO_BIN('20000000-0000-0000-0000-000000000002'),21456,'Aachen');

INSERT into fussballverein (id, vereinsname, email, mitglied_id, adresse_id, erzeugt, mannschaft)
VALUES
  (UUID_TO_BIN('00000000-0000-0000-0000-000000000000'), 'FC LigaStadt', 'ligastadt@web.de',
   UUID_TO_BIN('30000000-0000-0000-0000-000000000000'), UUID_TO_BIN('20000000-0000-0000-0000-000000000000'),
   '2023-01-15', 3);
