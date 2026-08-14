INSERT INTO users
(
    name,
    surname,
    age,
    email,
    password,
    phone_number,
    avatar,
    account_type,
    enabled,
    role_id
)
VALUES
    (
        'Aibek',
        'Asanov',
        22,
        'applicant@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700111222',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (
            SELECT id
            FROM roles
            WHERE role = 'APPLICANT'
            )
    ),
    (
        'Attractor',
        'Company',
        30,
        'employer@attractor.com',
        '$2y$10$9lYIDGlN6906dHEARifNPO7EPbkiDxts/FK2DIvTeB0Tk6Vco3IGC',
        '+996555333444',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (
            SELECT id
            FROM roles
            WHERE role = 'EMPLOYER'
            )
    );

INSERT INTO categories
(
    name,
    parent_id
)
VALUES
    ('IT', NULL),
    ('Design', NULL);

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
    ),
    (
        'UI/UX Design',
        (
            SELECT id
            FROM categories
            WHERE name = 'Design'
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
            WHERE email = 'applicant@attractor.com'
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
        TIMESTAMP '2026-07-20 12:00:00'
    ),
    (
        (
            SELECT id
            FROM users
            WHERE email = 'applicant@attractor.com'
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
            WHERE email = 'employer@attractor.com'
            ),
        TIMESTAMP '2026-07-10 09:00:00',
        TIMESTAMP '2026-07-20 11:00:00'
    ),
    (
        'Frontend Developer',
        'Разработка пользовательских интерфейсов',
        (
            SELECT id
            FROM categories
            WHERE name = 'Frontend Development'
            ),
        100000.00,
        1,
        3,
        TRUE,
        (
            SELECT id
            FROM users
            WHERE email = 'employer@attractor.com'
            ),
        TIMESTAMP '2026-07-11 09:30:00',
        TIMESTAMP '2026-07-21 14:00:00'
    );

INSERT INTO contact_types(type)
VALUES
    ('EMAIL'),
    ('PHONE'),
    ('TELEGRAM'),
    ('FACEBOOK'),
    ('LINKEDIN');

INSERT INTO contacts_info
(
    type_id,
    resume_id,
    "VALUE"
)
VALUES
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'EMAIL'
            ),
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Java Developer'
            ),
        'applicant@attractor.com'
    ),
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'PHONE'
            ),
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Java Developer'
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
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Java Developer'
            ),
        '@aibek_java'
    ),
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'LINKEDIN'
            ),
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Java Developer'
            ),
        'https://linkedin.com/in/aibek'
    ),
    (
        (
            SELECT id
            FROM contact_types
            WHERE type = 'EMAIL'
            ),
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Frontend Developer'
            ),
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
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Java Developer'
            ),
        'Attractor School',
        'Java Development',
        DATE '2025-09-01',
        DATE '2026-06-30',
        'Certificate'
    ),
    (
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Frontend Developer'
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
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Java Developer'
            ),
        1,
        'Freelance',
        'Junior Java Developer',
        'Разработка учебных приложений'
    ),
    (
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Frontend Developer'
            ),
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
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Java Developer'
            ),
        (
            SELECT v.id
            FROM vacancies v
                     JOIN users u ON u.id = v.author_id
            WHERE u.email = 'employer@attractor.com'
              AND v.name = 'Junior Java Developer'
            ),
        FALSE
    ),
    (
        (
            SELECT r.id
            FROM resumes r
                     JOIN users u ON u.id = r.applicant_id
            WHERE u.email = 'applicant@attractor.com'
              AND r.name = 'Frontend Developer'
            ),
        (
            SELECT v.id
            FROM vacancies v
                     JOIN users u ON u.id = v.author_id
            WHERE u.email = 'employer@attractor.com'
              AND v.name = 'Frontend Developer'
            ),
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
        (
            SELECT ra.id
            FROM responded_applicants ra
                     JOIN resumes r ON r.id = ra.resume_id
                     JOIN vacancies v ON v.id = ra.vacancy_id
                     JOIN users applicant ON applicant.id = r.applicant_id
                     JOIN users employer ON employer.id = v.author_id
            WHERE applicant.email = 'applicant@attractor.com'
              AND employer.email = 'employer@attractor.com'
              AND r.name = 'Java Developer'
              AND v.name = 'Junior Java Developer'
            ),
        'Здравствуйте! Хочу откликнуться на вакансию.',
        TIMESTAMP '2026-07-22 10:00:00'
    ),
    (
        (
            SELECT ra.id
            FROM responded_applicants ra
                     JOIN resumes r ON r.id = ra.resume_id
                     JOIN vacancies v ON v.id = ra.vacancy_id
                     JOIN users applicant ON applicant.id = r.applicant_id
                     JOIN users employer ON employer.id = v.author_id
            WHERE applicant.email = 'applicant@attractor.com'
              AND employer.email = 'employer@attractor.com'
              AND r.name = 'Java Developer'
              AND v.name = 'Junior Java Developer'
            ),
        'Здравствуйте! Мы рассмотрим ваше резюме.',
        TIMESTAMP '2026-07-22 10:15:00'
    );