CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    value INTEGER NOT NULL,
    publication_status SMALLINT DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE publication_status_mst (
    publication_status SMALLINT,
    publication_label VARCHAR(255)
);
INSERT INTO publication_status_mst (publication_status, publication_label) values (0, '未出版');
INSERT INTO publication_status_mst (publication_status, publication_label) values (1, '出版済み');

CREATE TABLE authors (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birthday DATE DEFAULT CURRENT_DATE NOT NULL
);

CREATE TABLE book_authors (
    book_id INTEGER NOT NULL,
    author_id INTEGER NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE
);
