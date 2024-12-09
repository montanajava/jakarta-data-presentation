-- noinspection SqlWithoutWhereForFile

SET search_path TO library, public;

CREATE EXTENSION if not exists unaccent;


DELETE FROM author_book;
DELETE FROM author;
DELETE FROM book;
DELETE FROM publisher;
DELETE FROM author_book;
DELETE FROM address;


insert into publisher (id, name) values (1, 'Manning');
INSERT INTO publisher (id, name) VALUES (2, 'RELX Group');
INSERT INTO publisher (id, name) VALUES (3, 'Thomson Reuters');
INSERT INTO publisher (id, name) VALUES (4, 'Pearson');
INSERT INTO publisher (id, name) VALUES (5, 'Penguin Random House');
INSERT INTO publisher (id, name) VALUES (6, 'Wolters Kluwer');



INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9781234567890', 'The SQL Sorcerer', 'Unleash your inner wizardry with SQL!', '2024-07-17', 500, 'Book', 300, 1);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9789876543210', 'Byte-sized Bites', 'Delicious recipes for optimizing code!', '2024-07-17', 200, 'Book', 125, 2);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9785432109876', 'The Data Detective', 'Solving mysteries with SQL clues!', '2024-07-17', 350, 'Book', 250, 3);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9781357924680', 'SQL and the City', 'Romantic escapades in SQL queries!', '2024-07-17', 300, 'Book', 200, 4);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9780246802468', 'Penguin Queries', 'Waddle your way to SQL mastery!', '2024-07-17', 250, 'Book', 180, 5);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9789876543219', 'SQL Zen', 'Find inner peace through elegant SQL!', '2024-07-17', 400, 'Book', 220, 6);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9789876543225', 'Chronicles of the Enchanted Codex', 'Within these ancient pages, mystical SQL spells await. Learn to query dragons and decipher eldritch databases!', '2024-07-17', 400, 'Journal', 150, 6);


INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9789876543226', 'Sorcerer''s Syntax', N'Unleash arcane JOINs and wield subqueries like a true SQL mage. Beware the NULL values—they hold secrets!', '2024-07-17', 400, 'Journal', 180, 1);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9788276543226', 'Realm of Query Quests', 'Embark on epic SQL adventures: Retrieve lost artifacts, battle nested loops, and decipher cryptic error messages.', '2024-07-17', 400, 'Journal', 200, 2);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9284847329468', 'Oracle''s Whispers', 'The ancient database foretells your fate. Seek INNER JOINs to unravel destiny, OUTER JOINs to rewrite it.', '2024-07-17', 400, 'Journal', 160, 3);


INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9789876543229', 'Wizardry of WHERE Clauses', N'Craft potions of precision: WHERE, LIKE, and BETWEEN. Beware the cursed Cartesian product—its wrath is NULL.', '2024-07-17', 400, 'Journal', 190, 4);


INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9789876543230', 'Scrolls of the SQL Sages', 'Learn from ancient masters: GROUP BY meditation, ORDER BY enlightenment, and the mystical UNION of knowledge.', '2024-07-17', 400, 'Journal', 170, 5);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9781932394153', 'Indexing the Cookbook', 'Find your favourite cookie recipies', '2024-08-09', 69800, 'Journal', 190, 5);

INSERT INTO book (isbn, title, description, creation_date, quantity_sold, type, pages, publisher_id)
VALUES ('9781932394887', 'The Sleepy Mapper', 'Find the object on the table', '2023-03-11', 3455, 'Journal', 101, 4);




insert into Author (ssn, name) values ('126-24-9867', 'Gavin King');
insert into Author (ssn, name) values ('XXX-YYY', 'Christian Bauer');
INSERT INTO Author (ssn, name) VALUES ('111-22-3333', 'Marlon Streep');
INSERT INTO Author (ssn, name) VALUES ('444-55-6666', 'Harrison Kidman');
INSERT INTO Author (ssn, name) VALUES ('777-88-9999', 'Natalie Hanks');
INSERT INTO Author (ssn, name) VALUES ('123-45-6789', 'Anthony Winslet');
INSERT INTO Author (ssn, name) VALUES ('123-45-6788', 'James Loren');
INSERT INTO Author (ssn, name) VALUES ('987-65-4321', 'Emma DiCaprio');
INSERT INTO Author (ssn, name) VALUES ('555-44-3333', 'Denzel Blanchett');


insert into Author_Book (author_ssn, book_isbn) values ('126-24-9867', '9781932394153');
insert into Author_Book (author_ssn, book_isbn) values ('126-24-9867', '9781932394887');
insert into Author_Book (author_ssn, book_isbn) values ('XXX-YYY', '9781932394153');
insert into Author_Book (author_ssn, book_isbn) values ('XXX-YYY', '9781932394887');

insert into Author_Book (author_ssn, book_isbn) values ('126-24-9867', '9781234567890');
insert into Author_Book (author_ssn, book_isbn) values ('XXX-YYY', '9789876543210');
insert into Author_Book (author_ssn, book_isbn) values ('111-22-3333', '9785432109876');

-- 2 authors for this book
insert into Author_Book (author_ssn, book_isbn) values ('444-55-6666', '9781357924680');
insert into Author_Book (author_ssn, book_isbn) values ('777-88-9999', '9781357924680');

-- 2 authors for this book
insert into Author_Book (author_ssn, book_isbn) values ('123-45-6789', '9780246802468');
insert into Author_Book (author_ssn, book_isbn) values ('987-65-4321', '9780246802468');

insert into Author_Book (author_ssn, book_isbn) values ('555-44-3333', '9789876543219');

-- 3 authors for this book
insert into Author_Book (author_ssn, book_isbn) values ('126-24-9867', '9789876543225');
insert into Author_Book (author_ssn, book_isbn) values ('XXX-YYY', '9789876543225');
insert into Author_Book (author_ssn, book_isbn) values ('111-22-3333', '9789876543225');

-- 3 authors for this book
insert into Author_Book (author_ssn, book_isbn) values ('444-55-6666', '9789876543226');
insert into Author_Book (author_ssn, book_isbn) values ('777-88-9999', '9789876543226');
insert into Author_Book (author_ssn, book_isbn) values ('123-45-6789', '9789876543226');

insert into Author_Book (author_ssn, book_isbn) values ('987-65-4321', '9788276543226');
insert into Author_Book (author_ssn, book_isbn) values ('555-44-3333', '9284847329468');
insert into Author_Book (author_ssn, book_isbn) values ('123-45-6788', '9789876543229');
insert into Author_Book (author_ssn, book_isbn) values ('126-24-9867', '9789876543230');

INSERT INTO Address (id) values (1);
INSERT INTO Address (id) values (2);
INSERT INTO Address (id) values (3);
INSERT INTO Address (id) values (4);

UPDATE Author SET address_id = 1 WHERE ssn = '126-24-9867';
UPDATE Author SET address_id = 2 WHERE ssn = 'XXX-YYY';
UPDATE Author SET address_id = 3 WHERE ssn = '111-22-3333';
UPDATE Author SET address_id = 4 WHERE ssn = '444-55-6666';
UPDATE Author SET address_id = 1 WHERE ssn = '777-88-9999';
UPDATE Author SET address_id = 2 WHERE ssn = '123-45-6789';
UPDATE Author SET address_id = 3 WHERE ssn = '123-45-6788';
UPDATE Author SET address_id = 4 WHERE ssn = '987-65-4321';
UPDATE Author SET address_id = 1 WHERE ssn = '555-44-3333';



