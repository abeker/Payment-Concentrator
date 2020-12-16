-- noinspection SqlDialectInspectionForFile
insert into address (city, country, zip_code, street, number, id) values
    ('Futog', 'Serbia', '21410', 'Pionirska', '4a', '6fe69dab-ed15-42cd-9237-06d8728fc788'),
    ('Sabac', 'Serbia', '271390', 'Macvanska', '7', '8f5389fa-417c-4333-b649-4c13540a29fa'),
    ('Novi Sad', 'Serbia', '24000', 'Jevrejska', 'bb', 'bae13b17-2ebb-4fc4-8b9c-1c2d073bd2a0');

-- accunt_number1: 4894549871600000
-- accunt_number2: 4894544568500485
insert into account (id, name, current_amount, account_number, address_id, date_closed, date_opened, bank_code) values
    ('aa692caa-255e-47b2-9401-28b47ae2a456', 'Pedro Neto', 10000, '$2y$12$8X4W4n0wR8PKnfROTwz6OuwyT.6QjyEyWOLk8ZQSw5cLWkGRyMleW', '6fe69dab-ed15-42cd-9237-06d8728fc788', null, '2020-05-21', '89454'),
    ('d5a76b76-11d2-4bd7-9771-7c4cecd71eec', 'Jack Grealish', 220000, '$2y$12$U.SI4swi7Z3LiHudbTOxsuAbm6qHUYCATxODuOzR9uON0FnM.FNqu', '8f5389fa-417c-4333-b649-4c13540a29fa', null, '2020-07-21', '89454');

-- security_code: 840
insert into customer_account (security_code, valid_thru, id) values
    ('$2y$12$o7NgCg379w63Emwl4UL7P.pcXtr6dsvTXDO6bUdYtaKmHZcD90iRa', '12/20', 'aa692caa-255e-47b2-9401-28b47ae2a456');

-- id: 3ypomvybMdZlWTZNVH1X9Tm35b4ETD, password:lBMIS5zIk1I2WsDyST3tVmgupt5utGHpFhj84l4ytosQqrv9wiK4XgpWDmTzpoA51FQBh2XmJm8RDhEIyc1F2slCBKFrm0QXdVww
insert into seller_account (error_url, failed_url, merchant_id, merchant_password, success_url, id)  values
    ('http://localhost:3000/pedro/error', 'http://localhost:3000/pedro/fail', '$2y$12$3.UWQMp.wI3jtmks24uR5u/sKDkLZINEQemLYaalnLJkLDa4.yuNu', '$2y$12$JO7vVMmsXQaNa8Ft.fFXIux/mIkv52flO.g1947O5F3FSV1TwaHDG', 'http://localhost:3000/pedro/success', 'd5a76b76-11d2-4bd7-9771-7c4cecd71eec');

insert into url_responder (id, date_closed, date_opened, payment_url) values
    ('796a554d-e834-43c7-86c3-9ad016edb637', '2020-07-20', '2020-01-01', 'http://localhost:8081/payment_raiffeisen'),
    ('e82081b4-eff2-467b-ad3a-1f8e85a2bdfa', null, '2020-07-20', 'http://localhost:3000/payment-raiffeisen');