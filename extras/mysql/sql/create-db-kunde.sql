-- Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program.  If not, see <https://www.gnu.org/licenses/>.

-- (1) docker compose exec mysql sh
-- (2) mysql --user=root --password=p < /sql/create-db-kunde.sql
-- (3) exit

-- mysqlsh ist *NICHT* im Docker-Image enthalten: https://dev.mysql.com/doc/refman/8.0/en/mysql.html

-- https://dev.mysql.com/doc/refman/8.0/en/create-user.html
-- https://dev.mysql.com/doc/refman/8.0/en/role-names.html
CREATE USER IF NOT EXISTS fussballverein IDENTIFIED BY 'p';
GRANT USAGE ON *.* TO fussballverein;

-- https://dev.mysql.com/doc/refman/8.0/en/create-database.html
-- https://dev.mysql.com/doc/refman/8.0/en/charset.html
-- SHOW CHARACTER SET;
CREATE DATABASE IF NOT EXISTS fussballverein CHARACTER SET utf8;

GRANT ALL PRIVILEGES ON fussballverein.* to fussballverein;

-- https://dev.mysql.com/doc/refman/8.0/en/create-tablespace.html
-- .idb-Datei innerhalb vom "data"-Verzeichnis
CREATE TABLESPACE `fussballvereinspace` ADD DATAFILE 'fussballvereinspace.ibd' ENGINE=INNODB;
