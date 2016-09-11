DROP TABLE IF EXISTS hmhb_user;

CREATE TABLE hmhb_user (
    id SERIAL PRIMARY KEY,
    email TEXT,
    first_name TEXT,
    last_name TEXT,
    birth_date DATE
);

/* Set the sequence to the 2000s so my data.sql can keep it's hardcoded 1001, 1002, etc. */
SELECT setval(pg_get_serial_sequence('hmhb_user', 'id'), 2000);
--SELECT setval('public.hmhb_user_id_seq', 2000);
