DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS responded_applicants;
DROP TABLE IF EXISTS work_experience_info;
DROP TABLE IF EXISTS education_info;
DROP TABLE IF EXISTS contacts_info;
DROP TABLE IF EXISTS contact_types;
DROP TABLE IF EXISTS vacancies;
DROP TABLE IF EXISTS resumes;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS customer;


CREATE TABLE customer
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(45),
    password VARCHAR(45)
);


CREATE TABLE users
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    surname      VARCHAR(100),
    age          INT,
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50)  NOT NULL,
    avatar       VARCHAR(255),
    account_type VARCHAR(20)  NOT NULL
);


CREATE TABLE categories
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    parent_id INT,

    FOREIGN KEY (parent_id)
        REFERENCES categories (id)
);


CREATE TABLE resumes
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    applicant_id INT            NOT NULL,
    name         VARCHAR(150)   NOT NULL,
    category_id  INT            NOT NULL,
    salary       DECIMAL(12, 2),
    is_active    BOOLEAN        NOT NULL,
    created_date TIMESTAMP      NOT NULL,
    update_time  TIMESTAMP      NOT NULL,

    FOREIGN KEY (applicant_id)
        REFERENCES users (id),

    FOREIGN KEY (category_id)
        REFERENCES categories (id)
);


CREATE TABLE vacancies
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(150)   NOT NULL,
    description  TEXT           NOT NULL,
    category_id  INT            NOT NULL,
    salary       DECIMAL(12, 2),
    exp_from     INT,
    exp_to       INT,
    is_active    BOOLEAN        NOT NULL,
    author_id    INT            NOT NULL,
    created_date TIMESTAMP      NOT NULL,
    update_time  TIMESTAMP      NOT NULL,

    FOREIGN KEY (category_id)
        REFERENCES categories (id),

    FOREIGN KEY (author_id)
        REFERENCES users (id)
);


CREATE TABLE contact_types
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL
);


CREATE TABLE contacts_info
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_id   INT          NOT NULL,
    resume_id INT          NOT NULL,
    "value"   VARCHAR(255) NOT NULL,

    FOREIGN KEY (type_id)
        REFERENCES contact_types (id),

    FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE CASCADE
);


CREATE TABLE education_info
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    resume_id   INT          NOT NULL,
    institution VARCHAR(255) NOT NULL,
    program     VARCHAR(255) NOT NULL,
    start_date  DATE         NOT NULL,
    end_date    DATE,
    degree      VARCHAR(100),

    FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE CASCADE
);


CREATE TABLE work_experience_info
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    resume_id        INT          NOT NULL,
    years            INT,
    company_name     VARCHAR(255) NOT NULL,
    position         VARCHAR(255) NOT NULL,
    responsibilities TEXT,

    FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE CASCADE
);


CREATE TABLE responded_applicants
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    resume_id    INT     NOT NULL,
    vacancy_id   INT     NOT NULL,
    confirmation BOOLEAN NOT NULL,

    FOREIGN KEY (resume_id)
        REFERENCES resumes (id)
        ON DELETE CASCADE,

    FOREIGN KEY (vacancy_id)
        REFERENCES vacancies (id)
        ON DELETE CASCADE,

    UNIQUE (resume_id, vacancy_id)
);


CREATE TABLE messages
(
    id                      INT AUTO_INCREMENT PRIMARY KEY,
    responded_applicant_id INT       NOT NULL,
    content                 TEXT      NOT NULL,
    "timestamp"             TIMESTAMP NOT NULL,

    FOREIGN KEY (responded_applicant_id)
        REFERENCES responded_applicants (id)
        ON DELETE CASCADE
);


INSERT INTO customer
(
    name,
    password
)
VALUES
    (
        'John Doe',
        'qwerty'
    ),
    (
        'Jane Doe',
        '123456'
    );


INSERT INTO users
(
    name,
    surname,
    age,
    email,
    password,
    phone_number,
    avatar,
    account_type
)
VALUES
    (
        'Aibek',
        'Asanov',
        22,
        'applicant@attractor.com',
        'qwerty123',
        '+996700111222',
        'default-avatar.png',
        'APPLICANT'
    ),
    (
        'Attractor',
        'Company',
        30,
        'employer@attractor.com',
        'qwerty123',
        '+996555333444',
        'default-avatar.png',
        'EMPLOYER'
    );


INSERT INTO categories
(
    name,
    parent_id
)
VALUES
    (
        'IT',
        NULL
    ),
    (
        'Design',
        NULL
    );


INSERT INTO categories
(
    name,
    parent_id
)
VALUES
    (
        'Backend Development',
        1
    ),
    (
        'Frontend Development',
        1
    ),
    (
        'UI/UX Design',
        2
    );


INSERT INTO resumes
(
    applicant_id,
    name,
    category_id,
    salary,
    is_active,
    created_date,
    update_time
)
VALUES
    (
        1,
        'Java Developer',
        3,
        90000.00,
        TRUE,
        TIMESTAMP '2026-07-01 10:00:00',
        TIMESTAMP '2026-07-20 12:00:00'
    ),
    (
        1,
        'Frontend Developer',
        4,
        80000.00,
        TRUE,
        TIMESTAMP '2026-07-02 11:00:00',
        TIMESTAMP '2026-07-21 13:00:00'
    );


INSERT INTO vacancies
(
    name,
    description,
    category_id,
    salary,
    exp_from,
    exp_to,
    is_active,
    author_id,
    created_date,
    update_time
)
VALUES
    (
        'Junior Java Developer',
        'Разработка приложений на Java и Spring Boot',
        3,
        120000.00,
        0,
        2,
        TRUE,
        2,
        TIMESTAMP '2026-07-10 09:00:00',
        TIMESTAMP '2026-07-20 11:00:00'
    ),
    (
        'Frontend Developer',
        'Разработка пользовательских интерфейсов',
        4,
        100000.00,
        1,
        3,
        TRUE,
        2,
        TIMESTAMP '2026-07-11 09:30:00',
        TIMESTAMP '2026-07-21 14:00:00'
    );


INSERT INTO contact_types
(
    type
)
VALUES
    (
        'EMAIL'
    ),
    (
        'PHONE'
    ),
    (
        'TELEGRAM'
    ),
    (
        'LINKEDIN'
    );


INSERT INTO contacts_info
(
    type_id,
    resume_id,
    "value"
)
VALUES
    (
        1,
        1,
        'applicant@attractor.com'
    ),
    (
        2,
        1,
        '+996700111222'
    ),
    (
        3,
        1,
        '@aibek_java'
    ),
    (
        1,
        2,
        'applicant@attractor.com'
    );


INSERT INTO education_info
(
    resume_id,
    institution,
    program,
    start_date,
    end_date,
    degree
)
VALUES
    (
        1,
        'Attractor School',
        'Java Development',
        DATE '2025-09-01',
        DATE '2026-06-30',
        'Certificate'
    ),
    (
        2,
        'Attractor School',
        'Frontend Development',
        DATE '2025-09-01',
        DATE '2026-06-30',
        'Certificate'
    );


INSERT INTO work_experience_info
(
    resume_id,
    years,
    company_name,
    position,
    responsibilities
)
VALUES
    (
        1,
        1,
        'Freelance',
        'Junior Java Developer',
        'Разработка учебных приложений'
    ),
    (
        2,
        1,
        'Freelance',
        'Junior Frontend Developer',
        'Разработка пользовательских интерфейсов'
    );


INSERT INTO responded_applicants
(
    resume_id,
    vacancy_id,
    confirmation
)
VALUES
    (
        1,
        1,
        FALSE
    ),
    (
        2,
        2,
        TRUE
    );


INSERT INTO messages
(
    responded_applicant_id,
    content,
    "timestamp"
)
VALUES
    (
        1,
        'Здравствуйте! Хочу откликнуться на вакансию.',
        TIMESTAMP '2026-07-22 10:00:00'
    ),
    (
        1,
        'Здравствуйте! Мы рассмотрим ваше резюме.',
        TIMESTAMP '2026-07-22 10:15:00'
    );