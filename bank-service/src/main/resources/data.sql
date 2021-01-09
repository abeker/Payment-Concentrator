-- noinspection SqlDialectInspectionForFile
insert into bank (id, name, bank_code, order_counter_acquirer, order_counter_issuer, payment_counter, deleted) values
    ('776cd924-84aa-42f6-82c6-e57082f931d2', 'unicredit', '99273', 0, 0, 0, false),
    ('9f5b5dc1-8092-4e55-9311-f21e44f8ff03', 'raiffeisen', '89454', 0, 0, 0, false);

insert into address (city, country, zip_code, street, number, id) values
    ('Novi Sad', 'Serbia', '21400', 'Cara Dusana', '4a', '6fe69dab-ed15-42cd-9237-06d8728fc788'),
    ('Belgrade', 'Serbia', '101801', 'Jurija Gagarina', '10', '8f5389fa-417c-4333-b649-4c13540a29fa'),
    ('Subotica', 'Serbia', '24000', 'Majksanski put', 'bb', 'bae13b17-2ebb-4fc4-8b9c-1c2d073bd2a0');

-- accunt_number1: 4992739871600000
-- accunt_number2: 4992734568500485
-- accunt_number1: 4894549871600000
-- accunt_number2: 4894544568500485
insert into account (id, name, current_amount, account_number, address_id, date_closed, date_opened, bank_code) values
    ('aa692caa-255e-47b2-9401-28b47ae2a456', 'Wilfred Zaha', 10000, '$2y$12$z4dJo4K.xZ3Io98YFibZqeZVMYvqVkCRXvHsS2MVne6bcA.ZCO49a', '6fe69dab-ed15-42cd-9237-06d8728fc788', null, '2020-05-21', '99273'),
    ('d5a76b76-11d2-4bd7-9771-7c4cecd71eec', 'Paulo Dybala', 220000, '$2y$12$bc28aQ4defndj8B5G7TAt.B3InQfDv4fuEOHyr2K8LHHx29atKUnG', '8f5389fa-417c-4333-b649-4c13540a29fa', null, '2020-07-21', '99273'),
    ('3571a16a-368a-4462-8e14-2a540445c45b', 'Pedro Neto', 10000, '$2y$12$8X4W4n0wR8PKnfROTwz6OuwyT.6QjyEyWOLk8ZQSw5cLWkGRyMleW', '6fe69dab-ed15-42cd-9237-06d8728fc788', null, '2020-05-21', '89454'),
    ('35add788-0ba5-4a56-922b-8d6f81e6bb38', 'Jack Grealish', 220000, '$2y$12$U.SI4swi7Z3LiHudbTOxsuAbm6qHUYCATxODuOzR9uON0FnM.FNqu', '8f5389fa-417c-4333-b649-4c13540a29fa', null, '2020-07-21', '89454');

-- security_code: 710
-- security_code: 840
insert into customer_account (security_code, valid_thru, id) values
    ('$2y$12$9YusfoG115jkbDZlfAD4i.7yiqXZhz.9E4r.mTUYfxDTYrkU9d13a', '12/20', 'aa692caa-255e-47b2-9401-28b47ae2a456'),
    ('$2y$12$o7NgCg379w63Emwl4UL7P.pcXtr6dsvTXDO6bUdYtaKmHZcD90iRa', '12/20', '3571a16a-368a-4462-8e14-2a540445c45b');

-- id: LMo0aUBivXliLs9rjBijU096ufdv56, password:p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9zQ
-- id: 3ypomvybMdZlWTZNVH1X9Tm35b4ETD, password:lBMIS5zIk1I2WsDyST3tVmgupt5utGHpFhj84l4ytosQqrv9wiK4XgpWDmTzpoA51FQBh2XmJm8RDhEIyc1F2slCBKFrm0QXdVww
insert into seller_account (error_url, failed_url, merchant_id, merchant_password, success_url, id)  values
    ('http://localhost:3000/paulo/error', 'http://localhost:3000/paulo/fail', '$2y$12$Es82vRCm.4kj0n81m1UpZeUFtfl5XeFd263T3T/0FPfonaqI5qbQ2', '$2y$12$d6onc4NspbXRVVr7Cm6tOOHWuI7ePuQnIqNp3U0o6jPkNc81fMjge', 'http://localhost:3000/paulo/success', 'd5a76b76-11d2-4bd7-9771-7c4cecd71eec'),
    ('http://localhost:3000/pedro/error', 'http://localhost:3000/pedro/fail', '$2y$12$3.UWQMp.wI3jtmks24uR5u/sKDkLZINEQemLYaalnLJkLDa4.yuNu', '$2y$12$JO7vVMmsXQaNa8Ft.fFXIux/mIkv52flO.g1947O5F3FSV1TwaHDG', 'http://localhost:3000/pedro/success', '35add788-0ba5-4a56-922b-8d6f81e6bb38');

insert into url_responder (id, date_closed, date_opened, payment_url) values
    ('796a554d-e834-43c7-86c3-9ad016edb637', '2020-07-20', '2020-01-01', '/payment_unicredit'),
    ('e82081b4-eff2-467b-ad3a-1f8e85a2bdfa', null, '2020-07-20', '/payment-unicredit'),
    ('25a549ec-f7a2-48d2-8052-f6c0d1bf21bd', '2020-07-20', '2020-01-01', '/payment_raiffeisen'),
    ('a255bf77-7424-411c-9a4e-8347330a595e', null, '2020-07-20', '/payment-raiffeisen');;