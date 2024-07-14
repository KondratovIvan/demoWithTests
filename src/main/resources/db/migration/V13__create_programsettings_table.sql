create table if not exists public.programsettings
(
    id         serial
        primary key,
    setting    varchar(255),
    value      varchar(255)
);
