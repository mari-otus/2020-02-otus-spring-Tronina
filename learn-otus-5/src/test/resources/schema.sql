drop table if exists book_author;
drop table if exists book_genre;
drop table if exists book;
drop table if exists genre;
drop table if exists author;

------------------------------
-- table: author            --
------------------------------
create table author
(
    id identity
        constraint author_pk
            primary key,
    fio text not null
);

comment on table author is 'Авторы';
comment on column author.fio is 'Фамилия имя отчество';

------------------------------
-- table: genre             --
------------------------------

create table genre
(
    id identity
        constraint genre_pk
            primary key,
    name text not null
);

comment on table genre is 'Жанры';
comment on column genre.name is 'Наименование жанра';

------------------------------
-- table: book              --
------------------------------

create table book
(
	id identity
		constraint book_pk
			primary key,
	name text not null,
	year_edition int
);

comment on table book is 'Книги';
comment on column book.name is 'Название книги';
comment on column book.year_edition is 'Год издания';

------------------------------
-- table: book_author       --
------------------------------

create table book_author
(
    book_id bigint,
    author_id bigint
);

comment on table book_author is 'Связь книг и авторов';
comment on column book_author.book_id is 'Идентификатор книги';
comment on column book_author.author_id is 'Идентификатор автора';

alter table book_author
    add constraint book_author_fk
        foreign key (book_id) references book
            on update cascade on delete cascade;

alter table book_author
    add constraint author_fk
        foreign key (author_id) references author
            on update cascade on delete cascade;

------------------------------
-- table: book_genre        --
------------------------------

create table book_genre
(
    book_id bigint,
    genre_id bigint
);

comment on table book_genre is 'Связь книг и жанров';
comment on column book_genre.book_id is 'Идентификатор книги';
comment on column book_genre.genre_id is 'Идентификатор жанра';

alter table book_genre
    add constraint book_genre_fk
        foreign key (book_id) references book
            on update cascade on delete cascade;

alter table book_genre
    add constraint genre_fk
        foreign key (genre_id) references genre
            on update cascade on delete no action;
