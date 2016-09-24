DROP TABLE IF EXISTS program;
DROP TABLE IF EXISTS organization;

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
    start_year INT NOT NULL,
    street_address TEXT NOT NULL,
    state TEXT NOT NULL,
    zip_code TEXT NOT NULL,
    created_on DATE NOT NULL,
    created_by TEXT NOT NULL,
    updated_on DATE,
    updated_by TEXT,
    UNIQUE (name, organization_id)
);
