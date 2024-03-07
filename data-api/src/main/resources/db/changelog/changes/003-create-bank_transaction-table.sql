--liquibase formatted sql
--changeset <olehkvasha>:<create-bank_transaction-table>
CREATE TABLE IF NOT EXISTS public.bank_transaction
(
    id bigint NOT NULL,
    creation_time timestamp NOT NULL,
    payment_id bigint NOT NULL,
    amount numeric NOT NULL,
    status character varying(20) NOT NULL,
    is_deleted boolean DEFAULT FALSE,
    CONSTRAINT bank_transaction_pk PRIMARY KEY (id),
    CONSTRAINT payment_fk FOREIGN KEY (payment_id)
        REFERENCES public.payment (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE bank_transaction;
