--liquibase formatted sql
--changeset <olehkvasha>:<init-bank_account-table>
INSERT INTO bank_account (id, owner, iban, account_number, amount)
VALUES (
        1,
        'KVASHA OLEH, 49124, Ukraine,region Dnipropetrovska,city Dnipro,street Semaforna,building 48,flat 57',
        'UA683052490263056400954301111',
        '5168745038101111',
        1000
        ),
       (
        2,
        'KVASHA SERGEY, 49124, Ukraine,region Dnipropetrovska,city Dnipro,street Semaforna,building 48,flat 57',
        'UA683052490263056400954302222',
        '5168745038102222',
        1000
       );

--rollback DELETE from bank_account WHERE id IN (1, 2);
