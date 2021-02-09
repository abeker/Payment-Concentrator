insert into permission (name) values
    ('LOGIN'), ('PAY_PAYPAL'), ('CREATE_PLAN_PAYPAL'), ('CREATE_PRODUCTS_PAYPAL'),     -- 1, 2, 3, 4
    ('PAY_BITCOIN'), ('SUBSCRIBE_BITCOIN'), ('CREATE_ORDER_BITCOIN'),     -- 5, 6, 7
    ('PAY_BANK'), ('PAYMENT_STATUS_BANK'), ('CANCEL_PAYMENT_BANK');      -- 8, 9, 10

insert into authority (name) values ('ROLE_BITCOIN'), ('ROLE_PAYPAL'), ('ROLE_BANK'), ('ROLE_SELLER');

insert into authorities_permissions (authority_id, permission_id) values
    (1, 5), (1, 6), (1, 7),
    (2, 2), (2, 3), (2, 4),
    (3, 8), (3, 9), (3, 10),
    (4, 1);

-- hash(d0c213f7-1e95-4ce4-8a65-334071e31ce8) => hsah(hash(D0g......))
insert into user_entity (id, deleted, secret, name, last_password_reset_date, password, user_role) values
    ('e47ca3f0-4906-495f-b508-4d9af7013575', false, '$2y$12$EuGJAkwDhO7EkLtptgtBdOUK/LJu4EwNeHsalwx6.lW0Za349XW.K', 'Vulkan', '2020-06-12 21:58:58.508-07', '$2y$12$CvC39ZJ9tnc/5109yMSg9epNYeHgGNrv9qG2ZTl7zPr9iaTqF.cWO', 'SELLER');

insert into user_authority (user_id, authority_id) values
    ('e47ca3f0-4906-495f-b508-4d9af7013575', 1),
    ('e47ca3f0-4906-495f-b508-4d9af7013575', 2),
    ('e47ca3f0-4906-495f-b508-4d9af7013575', 3),
    ('e47ca3f0-4906-495f-b508-4d9af7013575', 4);