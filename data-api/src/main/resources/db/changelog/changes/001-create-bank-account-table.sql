--liquibase formatted sql
--changeset <olehkvasha>:<create-bank_account-table>
CREATE TABLE IF NOT EXISTS public.bank_account
(
    id bigint NOT NULL,
    owner character varying(256) UNIQUE NOT NULL,
    iban character varying(30) UNIQUE NOT NULL,
    account_number character varying(16) UNIQUE NOT NULL,
    amount numeric DEFAULT 0,
    CONSTRAINT bank_account_pk PRIMARY KEY (id)
);

--rollback DROP TABLE bank_account;
