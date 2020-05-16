create table USER
(
    id         bigint primary key auto_increment,
    name       varchar(100),
    tel        varchar(20) unique,
    avatar     varchar(1024),
    address    varchar(1024),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;

insert into USER(id, name, tel, avatar, address)
values (1, 'test', '13800000000', 'http://url', '火星')