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
    name      VARCHAR(100) NOT NULL UNIQUE,
    parent_id INT,

    CONSTRAINT fk_categories_parent
        FOREIGN KEY (parent_id)
            REFERENCES categories (id)
);


CREATE TABLE resumes
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    applicant_id INT            NOT NULL,
    name         VARCHAR(150)   NOT NULL,
    category_id  INT            NOT NULL,
    salary       DECIMAL(12, 2) NOT NULL,
    is_active    BOOLEAN        NOT NULL DEFAULT TRUE,
    created_date TIMESTAMP      NOT NULL,
    update_time  TIMESTAMP      NOT NULL,

    CONSTRAINT fk_resumes_applicant
        FOREIGN KEY (applicant_id)
            REFERENCES users (id),

    CONSTRAINT fk_resumes_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
);


CREATE TABLE vacancies
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(150)   NOT NULL,
    description  TEXT           NOT NULL,
    category_id  INT            NOT NULL,
    salary       DECIMAL(12, 2) NOT NULL,
    exp_from     INT            NOT NULL,
    exp_to       INT            NOT NULL,
    is_active    BOOLEAN        NOT NULL DEFAULT TRUE,
    author_id    INT            NOT NULL,
    created_date TIMESTAMP      NOT NULL,
    update_time  TIMESTAMP      NOT NULL,

    CONSTRAINT fk_vacancies_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id),

    CONSTRAINT fk_vacancies_author
        FOREIGN KEY (author_id)
            REFERENCES users (id)
);


CREATE TABLE contact_types
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE contacts_info
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    type_id   INT          NOT NULL,
    resume_id INT          NOT NULL,
    "value"   VARCHAR(255) NOT NULL,

    CONSTRAINT fk_contacts_info_type
        FOREIGN KEY (type_id)
            REFERENCES contact_types (id),

    CONSTRAINT fk_contacts_info_resume
        FOREIGN KEY (resume_id)
            REFERENCES resumes (id)
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

    CONSTRAINT fk_education_info_resume
        FOREIGN KEY (resume_id)
            REFERENCES resumes (id)
);


CREATE TABLE work_experience_info
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    resume_id        INT          NOT NULL,
    years            INT          NOT NULL,
    company_name     VARCHAR(255) NOT NULL,
    position         VARCHAR(255) NOT NULL,
    responsibilities TEXT,

    CONSTRAINT fk_work_experience_info_resume
        FOREIGN KEY (resume_id)
            REFERENCES resumes (id)
);


CREATE TABLE responded_applicants
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    resume_id    INT     NOT NULL,
    vacancy_id   INT     NOT NULL,
    confirmation BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_responded_applicants_resume
        FOREIGN KEY (resume_id)
            REFERENCES resumes (id),

    CONSTRAINT fk_responded_applicants_vacancy
        FOREIGN KEY (vacancy_id)
            REFERENCES vacancies (id),

    CONSTRAINT uq_resume_vacancy
        UNIQUE (resume_id, vacancy_id)
);


CREATE TABLE messages
(
    id                      INT AUTO_INCREMENT PRIMARY KEY,
    responded_applicant_id INT       NOT NULL,
    content                 TEXT      NOT NULL,
    "timestamp"             TIMESTAMP NOT NULL,

    CONSTRAINT fk_messages_responded_applicant
        FOREIGN KEY (responded_applicant_id)
            REFERENCES responded_applicants (id)
);


CREATE TABLE customer
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(45),
    password VARCHAR(45)
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
        'applicant51@attractor.com',
        'applicant123',
        '+996700111222',
        'default-avatar.png',
        'APPLICANT'
    ),
    (
        'Alina',
        'Sadykova',
        28,
        'employer51@attractor.com',
        'employer123',
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
        (
            SELECT id
            FROM categories
            WHERE name = 'IT'
            )
    ),
    (
        'Frontend Development',
        (
            SELECT id
            FROM categories
            WHERE name = 'IT'
            )
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
        (
            SELECT id
            FROM users
            WHERE email = 'applicant51@attractor.com'
            ),
        'Java Developer',
        (
            SELECT id
            FROM categories
            WHERE name = 'Backend Development'
            ),
        90000.00,
        TRUE,
        TIMESTAMP '2026-07-01 10:00:00',
        TIMESTAMP '2026-07-15 12:30:00'
    ),
    (
        (
            SELECT id
            FROM users
            WHERE email = 'applicant51@attractor.com'
            ),
        'Frontend Developer',
        (
            SELECT id
            FROM categories
            WHERE name = 'Frontend Development'
            ),
        80000.00,
        TRUE,
        TIMESTAMP '2026-07-02 11:00:00',
        TIMESTAMP '2026-07-16 14:00:00'
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
        'Разработка веб-приложений на Java и Spring Boot.',
        (
            SELECT id
            FROM categories
            WHERE name = 'Backend Development'
            ),
        120000.00,
        0,
        2,
        TRUE,
        (
            SELECT id
            FROM users
            WHERE email = 'employer51@attractor.com'
            ),
        TIMESTAMP '2026-07-10 09:00:00',
        TIMESTAMP '2026-07-17 11:00:00'
    ),
    (
        'Junior Frontend Developer',
        'Разработка пользовательских интерфейсов.',
        (
            SELECT id
            FROM categories
            WHERE name = 'Frontend Development'
            ),
        100000.00,
        0,
        2,
        TRUE,
        (
            SELECT id
            FROM users
            WHERE email = 'employer51@attractor.com'
            ),
        TIMESTAMP '2026-07-11 09:30:00',
        TIMESTAMP '2026-07-16 15:00:00'
    );


INSERT INTO contact_types
(
    type
)
VALUES
    ('EMAIL'),
    ('PHONE'),
    ('TELEGRAM'),
    ('LINKEDIN');


INSERT INTO contacts_info
(
    type_id,
    resume_id,
    "value"
)
VALUES
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'EMAIL'
            ),
        (
            SELECT id
            FROM resumes
            WHERE name = 'Java Developer'
            ),
        'applicant51@attractor.com'
    ),
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'PHONE'
            ),
        (
            SELECT id
            FROM resumes
            WHERE name = 'Java Developer'
            ),
        '+996700111222'
    ),
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'TELEGRAM'
            ),
        (
            SELECT id
            FROM resumes
            WHERE name = 'Java Developer'
            ),
        '@applicant51'
    ),
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'EMAIL'
            ),
        (
            SELECT id
            FROM resumes
            WHERE name = 'Frontend Developer'
            ),
        'applicant51@attractor.com'
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
        (
            SELECT id
            FROM resumes
            WHERE name = 'Java Developer'
            ),
        'Attractor School',
        'Java Development',
        DATE '2025-09-01',
        DATE '2026-06-30',
        'Certificate'
    ),
    (
        (
            SELECT id
            FROM resumes
            WHERE name = 'Frontend Developer'
            ),
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
        (
            SELECT id
            FROM resumes
            WHERE name = 'Java Developer'
            ),
        1,
        'Freelance',
        'Junior Java Developer',
        'Разработка учебных приложений на Java и Spring Boot.'
    ),
    (
        (
            SELECT id
            FROM resumes
            WHERE name = 'Frontend Developer'
            ),
        1,
        'Freelance',
        'Junior Frontend Developer',
        'Разработка пользовательских интерфейсов.'
    );


INSERT INTO responded_applicants
(
    resume_id,
    vacancy_id,
    confirmation
)
VALUES
    (
        (
            SELECT id
            FROM resumes
            WHERE name = 'Java Developer'
            ),
        (
            SELECT id
            FROM vacancies
            WHERE name = 'Junior Java Developer'
            ),
        FALSE
    );


INSERT INTO messages
(
    responded_applicant_id,
    content,
    "timestamp"
)
VALUES
    (
        (
            SELECT ra.id
            FROM responded_applicants ra
                     JOIN resumes r
                          ON r.id = ra.resume_id
                     JOIN vacancies v
                          ON v.id = ra.vacancy_id
            WHERE r.name = 'Java Developer'
              AND v.name = 'Junior Java Developer'
            ),
        'Здравствуйте! Хочу откликнуться на вакансию.',
        TIMESTAMP '2026-07-17 12:00:00'
    ),
    (
        (
            SELECT ra.id
            FROM responded_applicants ra
                     JOIN resumes r
                          ON r.id = ra.resume_id
                     JOIN vacancies v
                          ON v.id = ra.vacancy_id
            WHERE r.name = 'Java Developer'
              AND v.name = 'Junior Java Developer'
            ),
        'Здравствуйте! Ваше резюме принято на рассмотрение.',
        TIMESTAMP '2026-07-17 12:15:00'
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