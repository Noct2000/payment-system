--liquibase formatted sql
--changeset <olehkvasha>:<init-bank_account-table>
INSERT INTO bank_account (id, full_name, iban, account_number, amount)
VALUES (
        1,
        'KVASHA OLEH',
        'UA683052490263056400954301111',
        '5168745038101111',
        1000
        ),
       (
        2,
        'KVASHA SERGEY',
        'UA683052490263056400954302222',
        '5168745038102222',
        1000
       );

--rollback DELETE from bank_account WHERE id IN (1, 2);
