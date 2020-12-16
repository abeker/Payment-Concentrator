-- noinspection SqlDialectInspectionForFile
insert into address (city, country, zip_code, street, number, id) values
    ('Novi Sad', 'Serbia', '21400', 'Cara Dusana', '4a', '6fe69dab-ed15-42cd-9237-06d8728fc788'),
    ('Belgrade', 'Serbia', '101801', 'Jurija Gagarina', '10', '8f5389fa-417c-4333-b649-4c13540a29fa'),
    ('Subotica', 'Serbia', '24000', 'Majksanski put', 'bb', 'bae13b17-2ebb-4fc4-8b9c-1c2d073bd2a0');

-- accunt_number1: 4992739871600000
-- accunt_number2: 4992734568500485
insert into account (id, name, current_amount, account_number, address_id, date_closed, date_opened, bank_code) values
    ('aa692caa-255e-47b2-9401-28b47ae2a456', 'Wilfred Zaha', 10000, '$2y$12$z4dJo4K.xZ3Io98YFibZqeZVMYvqVkCRXvHsS2MVne6bcA.ZCO49a', '6fe69dab-ed15-42cd-9237-06d8728fc788', null, '2020-05-21', '99273'),
    ('d5a76b76-11d2-4bd7-9771-7c4cecd71eec', 'Paulo Dybala', 220000, '$2y$12$bc28aQ4defndj8B5G7TAt.B3InQfDv4fuEOHyr2K8LHHx29atKUnG', '8f5389fa-417c-4333-b649-4c13540a29fa', null, '2020-07-21', '99273');

-- security_code: 710
insert into customer_account (security_code, valid_thru, id) values
    ('$2y$12$9YusfoG115jkbDZlfAD4i.7yiqXZhz.9E4r.mTUYfxDTYrkU9d13a', '12/20', 'aa692caa-255e-47b2-9401-28b47ae2a456');

-- id: LMo0aUBivXliLs9rjBijU096ufdv56, password:p62om0FvEhG70wzCjwrW6rsZCYSY9SikETjbpHNIrJ37Ul6odV4GgV025kFLP7Vwa79XJ8WTsAsDB2D3r9jW26G7a3zp78HbW9zQ
insert into seller_account (error_url, failed_url, merchant_id, merchant_password, success_url, id)  values
    ('http://localhost:3000/paulo/error', 'http://localhost:3000/paulo/fail', '$2y$12$Es82vRCm.4kj0n81m1UpZeUFtfl5XeFd263T3T/0FPfonaqI5qbQ2', '$2y$12$d6onc4NspbXRVVr7Cm6tOOHWuI7ePuQnIqNp3U0o6jPkNc81fMjge', 'http://localhost:3000/paulo/success', 'd5a76b76-11d2-4bd7-9771-7c4cecd71eec');

insert into url_responder (id, date_closed, date_opened, payment_url) values
    ('796a554d-e834-43c7-86c3-9ad016edb637', '2020-07-20', '2020-01-01', 'http://localhost:8081/payment_unicredit'),
    ('e82081b4-eff2-467b-ad3a-1f8e85a2bdfa', null, '2020-07-20', 'http://localhost:3000/payment-unicredit');