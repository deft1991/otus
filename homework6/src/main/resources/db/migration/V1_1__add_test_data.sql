INSERT INTO otus.author (ID, NAME) VALUES ('78870fde-8363-11e8-adc0-fa7ae01bbebc', 'Cristopher Rauly');
INSERT INTO otus.author (ID, NAME) VALUES ('78871132-8363-11e8-adc0-fa7ae01bbebc', 'John Tompson');

INSERT INTO otus.genre (ID, NAME) VALUES ('7887125e-8363-11e8-adc0-fa7ae01bbebc', 'horror');
INSERT INTO otus.genre (ID, NAME) VALUES ('7887138a-8363-11e8-adc0-fa7ae01bbebc', 'brainstorm');

INSERT INTO otus.book (ID, NAME) VALUES ('20dcddf2-3446-4b29-8b4b-0bcd020942fa', 'How not to write code or why you will be torn off your hands');
INSERT INTO otus.book (ID, NAME) VALUES ('78871132-8363-11e8-adc0-fa7ae01bbebc', 'Shoals of junas or burn in hell');
INSERT INTO otus.book (ID, NAME) VALUES ('d08d5edb-93cd-4c3e-9165-0f7675e7d380', 'Java pazlers');

INSERT INTO otus.book_to_author (ID, BOOK_ID, AUTHOR_ID) VALUES ('78871718-8363-11e8-adc0-fa7ae01bbebc', '20dcddf2-3446-4b29-8b4b-0bcd020942fa', '78870fde-8363-11e8-adc0-fa7ae01bbebc');
INSERT INTO otus.book_to_author (ID, BOOK_ID, AUTHOR_ID) VALUES ('7887184e-8363-11e8-adc0-fa7ae01bbebc', '78871132-8363-11e8-adc0-fa7ae01bbebc', '78871132-8363-11e8-adc0-fa7ae01bbebc');
INSERT INTO otus.book_to_author (ID, BOOK_ID, AUTHOR_ID) VALUES ('78871966-8363-11e8-adc0-fa7ae01bbebc', 'd08d5edb-93cd-4c3e-9165-0f7675e7d380', '78870fde-8363-11e8-adc0-fa7ae01bbebc');

INSERT INTO otus.book_to_genre (ID, BOOK_ID, GENRE_ID) VALUES ('78871718-8363-11e8-adc0-fa7ae01bbebc', '20dcddf2-3446-4b29-8b4b-0bcd020942fa', '7887125e-8363-11e8-adc0-fa7ae01bbebc');
INSERT INTO otus.book_to_genre (ID, BOOK_ID, GENRE_ID) VALUES ('7887184e-8363-11e8-adc0-fa7ae01bbebc', '78871132-8363-11e8-adc0-fa7ae01bbebc', '7887138a-8363-11e8-adc0-fa7ae01bbebc');
INSERT INTO otus.book_to_genre (ID, BOOK_ID, GENRE_ID) VALUES ('78871966-8363-11e8-adc0-fa7ae01bbebc', 'd08d5edb-93cd-4c3e-9165-0f7675e7d380', '7887125e-8363-11e8-adc0-fa7ae01bbebc');