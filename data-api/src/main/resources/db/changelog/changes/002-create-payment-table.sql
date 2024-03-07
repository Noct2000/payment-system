--liquibase formatted sql
--changeset <olehkvasha>:<create-payment-table>
CREATE TABLE IF NOT EXISTS public.payment
(
    id bigint NOT NULL,
    inn character varying(12) NOT NULL,
    payer_id bigint NOT NULL,
    recipient_id bigint NOT NULL,
    bic character varying(6) NOT NULL,
    okpo character varying(10) NOT NULL,
    mins_before_debiting bigint NOT NULL,
    amount numeric DEFAULT 0,
    is_deleted boolean DEFAULT FALSE,
    CONSTRAINT payment_pk PRIMARY KEY (id),
    CONSTRAINT payer_account_fk FOREIGN KEY (payer_id)
        REFERENCES public.bank_account (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT recipient_account_fk FOREIGN KEY (recipient_id)
        REFERENCES public.bank_account (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--rollback DROP TABLE payment;
