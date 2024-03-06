--liquibase formatted sql
--changeset <olehkvasha>:<create-bank_account-sequence-id>
CREATE SEQUENCE IF NOT EXISTS public.bank_account_id_seq INCREMENT 1 START 3 MINVALUE 1;

--rollback DROP SEQUENCE public.bank_account_id_seq;
