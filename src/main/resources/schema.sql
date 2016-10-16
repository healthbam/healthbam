-- noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS program_program_area;
DROP TABLE IF EXISTS program_county;
DROP TABLE IF EXISTS program;
DROP TABLE IF EXISTS program_area;
DROP TABLE IF EXISTS organization;
DROP TABLE IF EXISTS county;

CREATE TABLE organization (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    contact_phone TEXT,
    contact_email TEXT,
    website_url TEXT,
    facebook_url TEXT,
    created_on DATE NOT NULL,
    created_by TEXT NOT NULL,
    updated_on DATE,
    updated_by TEXT
);

CREATE TABLE program (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    organization_id BIGINT REFERENCES organization(id),
    start_year INT,
    street_address TEXT NOT NULL,
    city TEXT NOT NULL,
    state TEXT NOT NULL,
    zip_code TEXT NOT NULL,
    coordinates TEXT NOT NULL,
    created_on DATE NOT NULL,
    created_by TEXT NOT NULL,
    updated_on DATE,
    updated_by TEXT,
    UNIQUE (name, organization_id)
);

CREATE TABLE program_area (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE county (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    state TEXT NOT NULL,
    outer_boundary1 TEXT NOT NULL,
    inner_boundary1 TEXT,
    outer_boundary2 TEXT,
    outer_boundary3 TEXT,
    UNIQUE (name, state)
);

CREATE TABLE program_county (
    program_id BIGINT REFERENCES program(id),
    county_id BIGINT REFERENCES county(id),
    PRIMARY KEY(program_id, county_id)
);

CREATE TABLE program_program_area (
    program_id BIGINT REFERENCES program(id),
    program_area_id BIGINT REFERENCES program_area(id),
    PRIMARY KEY(program_id, program_area_id)
);
