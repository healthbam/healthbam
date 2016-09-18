DROP TABLE IF EXISTS hmhb_user; /* get rid of this in the next PR */

DROP TABLE IF EXISTS requested_program;
DROP TABLE IF EXISTS published_program;

CREATE TABLE published_program (
    id BIGSERIAL PRIMARY KEY,
    organization_name TEXT NOT NULL,
    mission TEXT,
    geo TEXT NOT NULL,
    /* look up geo data in map API */
    /* address info? */
    summary TEXT,
    outcomes TEXT,
    public_email TEXT,
    public_phone TEXT,
    private_email TEXT,
    private_phone TEXT,
    created_on DATE NOT NULL,
    created_by TEXT NOT NULL,
    updated_on DATE,
    updated_by TEXT
);

CREATE TABLE requested_program (
    id BIGSERIAL PRIMARY KEY,
    program_id BIGINT REFERENCES published_program(id),
    organization_name TEXT NOT NULL,
    mission TEXT,
    geo TEXT NOT NULL,
    /* look up geo data in map API */
    /* address info? */
    summary TEXT,
    outcomes TEXT,
    public_email TEXT,
    public_phone TEXT,
    private_email TEXT,
    private_phone TEXT,
    created_on DATE NOT NULL,
    created_by TEXT NOT NULL,
    updated_on DATE,
    updated_by TEXT
);
