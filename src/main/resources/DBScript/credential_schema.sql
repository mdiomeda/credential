CREATE SCHEMA IF NOT EXISTS credential;

CREATE TABLE IF NOT EXISTS credential.person (
  id bigint PRIMARY KEY,
  first_name text NOT NULL,
  last_name text NOT NULL,
  email text NOT NULL,
  phone text NULL,
  CONSTRAINT person_ck_email_lower_case CHECK (email = LOWER(email))
);

CREATE TABLE IF NOT EXISTS credential.user_account (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  password text NOT NULL,
  status text NOT NULL,
  person_id bigint NOT NULL REFERENCES credential.person(id)
);

CREATE TABLE IF NOT EXISTS credential.rol (
  id bigint PRIMARY KEY,
  name text NOT NULL,
  description text NULL
);

CREATE TABLE IF NOT EXISTS credential.rol_user_account (
  user_account_id bigint NOT NULL REFERENCES credential.user_account(id),
  rol_id bigint  NOT NULL REFERENCES credential.rol(id),
  CONSTRAINT rol_user_pkey PRIMARY KEY (user_account_id, rol_id)
  );


CREATE INDEX user_account_ix_name ON credential.user_account (name);
CREATE INDEX rol_ix_name ON credential.rol (name);
CREATE INDEX person_ix_last_name ON credential.person (last_name);

CREATE SEQUENCE credential.user_account_id_seq
  start with 1
  increment by 1
  maxvalue 99999
  minvalue 1;
  
CREATE SEQUENCE credential.rol_id_seq
  start with 1
  increment by 1
  maxvalue 99999
  minvalue 1;
