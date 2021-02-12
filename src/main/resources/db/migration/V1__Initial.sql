create table account
(
    id                       bigint auto_increment
        primary key,
    account_currency         varchar(255) null,
    account_name             varchar(255) null,
    account_type             varchar(255) null,
    balance                  double       not null,
    created_date_time        datetime     null,
    credit_limit             double       not null,
    description              varchar(255) null,
    enable_schedule          bit          not null,
    include_in_total_balance bit          not null
);

create table category
(
    id               bigint auto_increment
        primary key,
    category_balance double       not null,
    currency         varchar(255) null,
    description      varchar(255) null,
    name             varchar(255) null,
    type             varchar(255) null
);

create table transaction
(
    id                 bigint auto_increment
        primary key,
    created_date_time  datetime null,
    transaction_amount double   not null,
    account_id         bigint   null,
    category_id        bigint   null,
    dest_account_id    bigint   null,
    constraint FK6g20fcr3bhr6bihgy24rq1r1b
        foreign key (account_id) references account (id),
    constraint FK72dlbg1lyexxqh6vs34uhjal9
        foreign key (dest_account_id) references account (id),
    constraint FKgik7ruym8r1n4xngrclc6kiih
        foreign key (category_id) references category (id)
);

create table authority
(
    name varchar(255) not null
        primary key
);

create table user
(
    id         bigint auto_increment
        primary key,
    activated  bit          not null,
    email      varchar(255) null,
    first_name varchar(255) null,
    last_name  varchar(255) null,
    password   varchar(255) null,
    username   varchar(255) null
);

create table user_authority
(
    user_id        bigint       not null,
    authority_name varchar(255) not null,
    primary key (user_id, authority_name),
    constraint FK6ktglpl5mjosa283rvken2py5
        foreign key (authority_name) references authority (name),
    constraint FKpqlsjpkybgos9w2svcri7j8xy
        foreign key (user_id) references user (id)
);

INSERT INTO account (id, account_currency, account_name, account_type, balance, created_date_time, credit_limit, description, enable_schedule, include_in_total_balance) VALUES (1, 'AZN', 'Asdasd', 'REGULAR', 10000, null, 1000, 'sadvasdn', true, true);
INSERT INTO account (id, account_currency, account_name, account_type, balance, created_date_time, credit_limit, description, enable_schedule, include_in_total_balance) VALUES (2, 'AZN', 'ASDAD', 'REGULAR', 5999, null, 212, 'asdad', true, true);
INSERT INTO category (id, category_balance, currency, description, name, type) VALUES (1, 2000, 'AZN', 'asdasd', 'sdaasd', 'EXPENSE');
INSERT INTO category (id, category_balance, currency, description, name, type) VALUES (2, 121, 'AZN', 'asda', 'asda', 'EXPENSE');
INSERT INTO category (id, category_balance, currency, description, name, type) VALUES (3, 1231, 'AZN', 'asdad', 'asda', 'EXPENSE');
INSERT INTO transaction (id, created_date_time, transaction_amount, account_id, category_id, dest_account_id) VALUES (1, null, 299, 1, 1, null);
INSERT INTO transaction (id, created_date_time, transaction_amount, account_id, category_id, dest_account_id) VALUES (2, null, 787, 1, 2, null);
INSERT INTO transaction (id, created_date_time, transaction_amount, account_id, category_id, dest_account_id) VALUES (3, null, 768, 2, 2, null);

INSERT INTO user (id, username, password, first_name, last_name, email, activated) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 'admin@admin.com', 1);
INSERT INTO user (id, username, password, first_name, last_name, email, activated) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'enabled@user.com', 1);
INSERT INTO user (id, username, password, first_name, last_name, email, activated) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_name) VALUES (1, 'ROLE_USER');
INSERT INTO user_authority (user_id, authority_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_authority (user_id, authority_name) VALUES (2, 'ROLE_USER');
INSERT INTO user_authority (user_id, authority_name) VALUES (3, 'ROLE_USER');
