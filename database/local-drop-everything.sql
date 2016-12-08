/* First drop the database owned by our user. */
DROP DATABASE hmhb_db;

/* Next, drop any privileges owned by our user. */
DROP OWNED BY healthbam_sql;

/* Last, drop our user. */
DROP USER healthbam_sql;
