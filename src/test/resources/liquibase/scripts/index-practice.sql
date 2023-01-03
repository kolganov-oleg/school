-- liquibase formatted sql

-- changeset oKolganov:1
CREATE INDEX student_name_index ON student (name);

-- changeset oKolganov:2
CREATE INDEX faculty_name_color_index ON faculty (name, color);