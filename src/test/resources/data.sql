CREATE SEQUENCE cat_sequence;

CREATE TABLE cat_table (
   microchip_number BIGINT NOT NULL, -- BIGINT will cover max length of 15
   name VARCHAR,
   breed VARCHAR,
   type VARCHAR,
   primary_color VARCHAR,
   sex CHAR,
   age TINYINT,
   deceased BOOLEAN,
   created_timestamp TIMESTAMP,
   updated_timestamp TIMESTAMP,
   PRIMARY KEY(microchip_number));

INSERT INTO cat_table (microchip_number, name, breed, primary_color, sex, age, deceased) VALUES (123456789000001, 'Snowball', 'Devon Rex', 'White', 'F', 5, false);
INSERT INTO cat_table (microchip_number, name, breed, type, primary_color, sex, age, deceased) VALUES (123456789000002, 'Cattitude', 'Domestic Short Hair', 'Calico', 'Orange, Black, White', 'M', 2, false);