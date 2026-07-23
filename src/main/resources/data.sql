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
    "VALUE"
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
    "TIMESTAMP"
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