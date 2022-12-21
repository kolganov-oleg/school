CREATE TABLE persons (
                         person_id SERIAL PRIMARY KEY,
                         name VARCHAR(40) NOT NULL,
                         age INTEGER,
                         have_driver_license BOOLEAN DEFAULT FALSE,
                         car_id INTEGER NOT NULL
);

ALTER TABLE persons
    ADD CONSTRAINT age_constraint CHECK (age >= 0);

CREATE TABLE cars (
                      car_id SERIAL PRIMARY KEY,
                      brand VARCHAR(40) NOT NULL,
                      model VARCHAR(40) NOT NULL,
                      cost MONEY NOT NULL
);

ALTER TABLE cars
    ADD CONSTRAINT cars_unique UNIQUE (brand, model);