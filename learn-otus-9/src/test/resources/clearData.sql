delete from book_author;
delete from book_genre;
delete from comment_book;
delete from book;
delete from genre;
delete from author;

alter sequence if exists author_id_seq restart with 10;
alter sequence if exists genre_id_seq restart with 10;
alter sequence if exists book_id_seq restart with 10;
alter sequence if exists comment_book_id_seq restart with 10;
