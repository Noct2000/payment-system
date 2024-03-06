--liquibase formatted sql
--changeset <olehkvasha>:<create-bank_transaction-sequence-id>
CREATE SEQUENCE IF NOT EXISTS public.bank_transaction_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.bank_transaction_seq;
