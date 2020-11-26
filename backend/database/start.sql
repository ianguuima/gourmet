create table if not exists dish
(
    id          bigserial not null,
    name        text      not null,
    ingredients text[]    not null
);

alter table dish
    owner to postgres;

create unique index dish_id_uindex
    on dish (id);