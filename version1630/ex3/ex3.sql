create table Category (
    id serial not null primary key,
    name varchar(255)
);

create table Author (
    id serial not null primary key,
    firstname varchar(255),
    lastname varchar(255)
);

create table Book (
    id  serial not null primary key,
    title varchar(255),
    author_id int references Author(id),
    category_id int references Category(id)
);

create table Member (
    id  serial not null primary key,
    password varchar(255),
    username varchar(255),
    firstname varchar(255),
    lastname varchar(255)
);

create table Book_comments (
    Book_id int not null references Book(id),
    Author_id int references Member(id),
    date timestamp,
    text varchar(2000)
);

create table Member_Category (
    Member_id int not null references Member(id),
    preferences_id int not null references Category(id)
);

create table Reservation (
    id serial not null,
    pickupDate date,
    returnDate date,
    book_id int references Book(id),
    member_id int references Member(id)
);

INSERT INTO category (name) VALUES ('Essai');
INSERT INTO category (name) VALUES ('Roman');
INSERT INTO category (name) VALUES ('Poésie');
INSERT INTO category (name) VALUES ('Biographie');



INSERT INTO author (firstname, lastname) VALUES ('J.R', 'Tokien');
INSERT INTO author (firstname, lastname) VALUES ('Emile', 'Zola');
INSERT INTO author (firstname, lastname) VALUES ('Ernesto', 'Guevara');
INSERT INTO author (firstname, lastname) VALUES ('Henry David', 'Thoreau');
INSERT INTO author (firstname, lastname) VALUES ('Albert', 'Cohen');
INSERT INTO author (firstname, lastname) VALUES ('Arthur', 'Rimbaud');
INSERT INTO author (firstname, lastname) VALUES ('Miguel', 'de Cervantes');
INSERT INTO author (firstname, lastname) VALUES ('Ernest', 'Hemingway');
INSERT INTO author (firstname, lastname) VALUES ('Leon', 'Tolstoi');
INSERT INTO author (firstname, lastname) VALUES ('Alexandre', 'Dumas');
INSERT INTO author (firstname, lastname) VALUES ('Charles', 'Baudelaire');
INSERT INTO author (firstname, lastname) VALUES ('Franz', 'Kafka');
INSERT INTO author (firstname, lastname) VALUES ('Stefan', 'Zweig');
INSERT INTO author (firstname, lastname) VALUES ('Jack', 'London');
INSERT INTO author (firstname, lastname) VALUES ('Joseph', 'Kessel');
INSERT INTO author (firstname, lastname) VALUES ('Thomas', 'Mann');
INSERT INTO author (firstname, lastname) VALUES ('Jorge', 'Semprun');
INSERT INTO author (firstname, lastname) VALUES ('Gabriel', 'Garcia Marquez');
INSERT INTO author (firstname, lastname) VALUES ('William', 'Faulkner');
INSERT INTO author (firstname, lastname) VALUES ('Giuseppe Tomasi', 'di Lampedusa');
INSERT INTO author (firstname, lastname) VALUES ('Joseph', 'Conrad');
INSERT INTO author (firstname, lastname) VALUES ('Romain', 'Gary');
INSERT INTO author (firstname, lastname) VALUES ('Victor', 'Hugo');
INSERT INTO author (firstname, lastname) VALUES ('Walt', 'Withman');
INSERT INTO author (firstname, lastname) VALUES ('Fedor', 'Dostoievski');
INSERT INTO author (firstname, lastname) VALUES ('James', 'Joyce');
INSERT INTO author (firstname, lastname) VALUES ('Vladimir', 'Nabokov');
INSERT INTO author (firstname, lastname) VALUES ('Charles', 'Dickens');
INSERT INTO author (firstname, lastname) VALUES ('Marcel', 'Proust');
INSERT INTO author (firstname, lastname) VALUES ('Honoré', 'de Balzac');



INSERT INTO book (title, author_id, category_id) VALUES ('Walden', 4, 1);
INSERT INTO book (title, author_id, category_id) VALUES ('L''ecriture ou la vie', 17, 1);
INSERT INTO book (title, author_id, category_id) VALUES ('Cent ans de solitude', 18, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Don Quichotte', 7, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Illusions perdues', 30, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('La recherche du temps perdu', 29, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('De grandes espérances', 28, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Lolita', 27, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Ulysse', 26, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les frères Karamazov', 25, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les misérables', 23, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les racines du ciel', 22, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Au coeur des ténèbres', 21, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le bruit et la fureur', 19, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('La montagne magique', 16, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Les cavaliers', 15, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le procès', 12, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le comte de Monte Christo', 10, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Guerre et paix', 9, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le vieil homme et la mer', 8, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Belle du seigneur', 5, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Germinal', 2, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Le seigneur des anneaux', 1, 2);
INSERT INTO book (title, author_id, category_id) VALUES ('Feuilles d''herbe', 24, 3);
INSERT INTO book (title, author_id, category_id) VALUES ('Les fleurs du mal', 11, 3);
INSERT INTO book (title, author_id, category_id) VALUES ('Une saison en enfer', 6, 3);
INSERT INTO book (title, author_id, category_id) VALUES ('Le guépard', 20, 4);
INSERT INTO book (title, author_id, category_id) VALUES ('Martin Eden', 14, 4);
INSERT INTO book (title, author_id, category_id) VALUES ('Magellan', 13, 4);
INSERT INTO book (title, author_id, category_id) VALUES ('Carnets de voyage', 3, 4);

INSERT INTO Member(firstname, lastname, username, password) VALUES('John', 'Doe', 'jdoe', 'PBKDF2WithHmacSHA256:2048:g7SLH1i2VdtaRnOZjC7ejS3VCdvRE9g8F8EYMSlIX7g=:3fp2AOPR5JYb6TPNtHJzLWhzDUDRpZYVvDMJO8joG4Y=');
INSERT INTO Book_comments(book_id, author_id, date, text) values(1,1, now(), 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."');

