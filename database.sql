DROP TABLE IF EXISTS productbatch_component;
DROP TABLE IF EXISTS productbatch;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS formula_component;
DROP TABLE IF EXISTS formula;
DROP TABLE IF EXISTS materialbatch;
DROP TABLE IF EXISTS material;

CREATE TABLE `user`(user_id INT PRIMARY KEY, user_name TEXT NOT NULL, ini TEXT NOT NULL, cpr VARCHAR(10) NOT NULL UNIQUE, password TEXT NOT NULL, role INT NOT NULL, status INT NOT NULL) ENGINE=innoDB;
 
CREATE TABLE material(material_id INT PRIMARY KEY, material_name TEXT NOT NULL, provider TEXT NOT NULL) ENGINE=innoDB;

CREATE TABLE materialbatch(mb_id INT PRIMARY KEY, material_id INT NOT NULL, quantity REAL NOT NULL, 
   FOREIGN KEY (material_id) REFERENCES material(material_id)) ENGINE=innoDB;

CREATE TABLE formula(formula_id INT PRIMARY KEY, formula_name TEXT) ENGINE=innoDB;

CREATE TABLE formula_component(formula_id INT NOT NULL, material_id INT NOT NULL, nom_netto REAL NOT NULL, tolerance REAL NOT NULL, 
   PRIMARY KEY (formula_id, material_id), 
   FOREIGN KEY (formula_id) REFERENCES formula(formula_id), 
   FOREIGN KEY (material_id) REFERENCES material(material_id)) ENGINE=innoDB;

CREATE TABLE productbatch(pb_id INT PRIMARY KEY, status INT NOT NULL, formula_id INT NOT NULL, 
   FOREIGN KEY (formula_id) REFERENCES formula(formula_id)) ENGINE=innoDB;

CREATE TABLE productbatch_component(pb_id INT, mb_id INT, tare REAL NOT NULL, netto REAL NOT NULL, user_id INT NOT NULL, 
   PRIMARY KEY (pb_id, mb_id), 
   FOREIGN KEY (pb_id) REFERENCES productbatch(pb_id), 
   FOREIGN KEY (mb_id) REFERENCES materialbatch(mb_id), 
   FOREIGN KEY (user_id) REFERENCES `user`(user_id)) ENGINE=innoDB;


INSERT INTO `user`(user_id, user_name, ini, cpr, password, role, status) VALUES
(1, 'Angelo A', 'AA', '1701114234', '#DTU#cdio2015', 1, 1),
(2, 'Antonella B', 'AB', '1801114234', '#DTU#cdio2015', 2, 1),
(3, 'Luigi C', 'AC', '1901114234', '#DTU#cdio2015', 3, 1);
(4, 'Mario D', 'AD', '0102114234', '#DTU#cdio2015', 4, 1);
(5, 'Mads Nyborg', 'MN', '1911991123', '#GWT#cdio2015', 1, 0);

INSERT INTO material(material_id, material_name, provider) VALUES
(1, 'dej', 'Wawelka'),
(2, 'tomat', 'Knoor'),
(3, 'tomat', 'Veaubais'),
(4, 'tomat', 'Franz'),
(5, 'ost', 'Ost og Skinke A/S'),
(6, 'skinke', 'Ost og Skinke A/S'),
(7, 'champignon', 'Igloo Frostvarer');

INSERT INTO materialbatch(mb_id, material_id, quantity) VALUES
(1, 1, 1.5),
(2, 2, 4.5),
(3, 3, 2.32),
(4, 5, 3.31),
(5, 5, 0.127), 
(6, 6, 0.321),
(7, 7, 5.93);

INSERT INTO formula(formula_id, formula_name) VALUES
(1, 'margherita'),
(2, 'prosciutto'),
(3, 'capricciosa');


INSERT INTO formula_component(formula_id, material_id, nom_netto, tolerance) VALUES
(1, 1, 10.0, 0.1),
(1, 2, 2.0, 0.1),
(1, 5, 2.0, 0.1),

(2, 1, 10.0, 0.1),
(2, 3, 2.0, 0.1),  
(2, 5, 1.5, 0.1),
(2, 6, 1.5, 0.1),

(3, 1, 10.0, 0.1),
(3, 4, 1.5, 0.1),
(3, 5, 1.5, 0.1),
(3, 6, 1.0, 0.1),
(3, 7, 1.0, 0.1);

INSERT INTO productbatch(pb_id, formula_id, status) VALUES
(1, 1, 3), 
(2, 1, 3),
(3, 2, 2),
(4, 3, 1),
(5, 3, 1);


INSERT INTO productbatch_component(pb_id, mb_id, tare, netto, user_id) VALUES
(1, 1, 0.5, 5.05, 4),
(1, 2, 0.5, 2.03, 4),
(1, 4, 0.5, 1.98, 4),

(2, 1, 0.5, 5.01, 4),
(2, 2, 0.5, 1.99, 4),
(2, 5, 0.5, 1.47, 4),

(3, 1, 0.5, 5.07, 4),
(3, 3, 0.5, 2.06, 4),
(3, 4, 0.5, 1.55, 4),
(3, 6, 0.5, 1.53, 4),

(4, 1, 0.5, 5.02, 4),
(4, 5, 0.5, 1.57, 4),
(4, 6, 0.5, 1.03, 4),
(4, 7, 0.5, 0.99, 4);