--liquibase formatted sql
--changeset <olehkvasha>:<create-payment-sequence-id>
CREATE SEQUENCE IF NOT EXISTS public.payment_id_seq INCREMENT 1 START 1 MINVALUE 1;

--rollback DROP SEQUENCE public.payment_id_seq;
