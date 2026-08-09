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
        'Nursultan',
        'Omurzakov',
        23,
        'nursultan.omurzakov@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100001',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Aizhan',
        'Toktogulova',
        24,
        'aizhan.toktogulova@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100002',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Bekzat',
        'Isakov',
        25,
        'bekzat.isakov@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100003',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Aigerim',
        'Sadykova',
        22,
        'aigerim.sadykova@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100004',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Timur',
        'Abdyldaev',
        26,
        'timur.abdyldaev@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100005',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Meerim',
        'Kubanychbekova',
        24,
        'meerim.kubanychbekova@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100006',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Adilet',
        'Turgunbaev',
        27,
        'adilet.turgunbaev@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100007',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Eliza',
        'Asanova',
        23,
        'eliza.asanova@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100008',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Daniyar',
        'Mambetaliev',
        28,
        'daniyar.mambetaliev@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100009',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Begimai',
        'Ergeshova',
        22,
        'begimai.ergeshova@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100010',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Mirlan',
        'Osmonov',
        29,
        'mirlan.osmonov@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100011',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Alina',
        'Jumabaeva',
        24,
        'alina.jumabaeva@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100012',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Eldar',
        'Satarov',
        26,
        'eldar.satarov@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100013',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),
    (
        'Nurai',
        'Bekova',
        23,
        'nurai.bekova@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996700100014',
        'default-avatar.png',
        'APPLICANT',
        TRUE,
        (SELECT id FROM roles WHERE role = 'APPLICANT')
    ),

    (
        'Nomad Tech',
        NULL,
        30,
        'nomad.tech@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200001',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Ala-Too Digital',
        NULL,
        31,
        'alatoo.digital@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200002',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Bishkek Soft',
        NULL,
        32,
        'bishkek.soft@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200003',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Tunduk Labs',
        NULL,
        29,
        'tunduk.labs@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200004',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Manas Systems',
        NULL,
        34,
        'manas.systems@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200005',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Silk Road IT',
        NULL,
        30,
        'silkroad.it@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200006',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Kyrgyz Code',
        NULL,
        33,
        'kyrgyz.code@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200007',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Peak Solutions',
        NULL,
        28,
        'peak.solutions@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200008',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Ak-Keme Digital',
        NULL,
        35,
        'akkeme.digital@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200009',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Issyk-Kul Tech',
        NULL,
        31,
        'issykul.tech@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200010',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Ordo Systems',
        NULL,
        32,
        'ordo.systems@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200011',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Berkut Software',
        NULL,
        30,
        'berkut.software@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200012',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Jeti-Oguz Labs',
        NULL,
        29,
        'jetioguz.labs@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200013',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
    ),
    (
        'Central Asia Dev',
        NULL,
        34,
        'centralasia.dev@attractor.com',
        '$2y$10$rGEITnrNBARrzDPTddHfvu/kvEzvgTlJI/QKNT0BGLKsBEFm/kaWC',
        '+996555200014',
        'default-avatar.png',
        'EMPLOYER',
        TRUE,
        (SELECT id FROM roles WHERE role = 'EMPLOYER')
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
        (SELECT id FROM users WHERE email = 'nursultan.omurzakov@attractor.com'),
        'Java Backend Developer',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        110000.00,
        TRUE,
        TIMESTAMP '2026-07-27 09:00:00',
        TIMESTAMP '2026-07-27 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'aizhan.toktogulova@attractor.com'),
        'Frontend React Developer',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        95000.00,
        TRUE,
        TIMESTAMP '2026-07-28 09:00:00',
        TIMESTAMP '2026-07-28 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'bekzat.isakov@attractor.com'),
        'UI UX Designer',
        (SELECT id FROM categories WHERE name = 'UI/UX Design'),
        85000.00,
        TRUE,
        TIMESTAMP '2026-07-29 09:00:00',
        TIMESTAMP '2026-07-29 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'aigerim.sadykova@attractor.com'),
        'Junior Java Developer',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        70000.00,
        TRUE,
        TIMESTAMP '2026-07-30 09:00:00',
        TIMESTAMP '2026-07-30 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'timur.abdyldaev@attractor.com'),
        'Spring Boot Developer',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        130000.00,
        TRUE,
        TIMESTAMP '2026-07-31 09:00:00',
        TIMESTAMP '2026-07-31 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'meerim.kubanychbekova@attractor.com'),
        'Frontend Web Developer',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        105000.00,
        TRUE,
        TIMESTAMP '2026-08-01 09:00:00',
        TIMESTAMP '2026-08-01 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'adilet.turgunbaev@attractor.com'),
        'Backend Engineer',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        150000.00,
        TRUE,
        TIMESTAMP '2026-08-02 09:00:00',
        TIMESTAMP '2026-08-02 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'eliza.asanova@attractor.com'),
        'Product UI Designer',
        (SELECT id FROM categories WHERE name = 'UI/UX Design'),
        100000.00,
        TRUE,
        TIMESTAMP '2026-08-03 09:00:00',
        TIMESTAMP '2026-08-03 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'daniyar.mambetaliev@attractor.com'),
        'Senior Java Developer',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        200000.00,
        TRUE,
        TIMESTAMP '2026-08-04 09:00:00',
        TIMESTAMP '2026-08-04 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'begimai.ergeshova@attractor.com'),
        'Junior Frontend Developer',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        65000.00,
        TRUE,
        TIMESTAMP '2026-08-05 09:00:00',
        TIMESTAMP '2026-08-05 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'mirlan.osmonov@attractor.com'),
        'Java Software Engineer',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        175000.00,
        TRUE,
        TIMESTAMP '2026-08-06 09:00:00',
        TIMESTAMP '2026-08-06 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'alina.jumabaeva@attractor.com'),
        'UX Researcher Designer',
        (SELECT id FROM categories WHERE name = 'UI/UX Design'),
        115000.00,
        TRUE,
        TIMESTAMP '2026-08-07 09:00:00',
        TIMESTAMP '2026-08-07 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'eldar.satarov@attractor.com'),
        'Fullstack Java Developer',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        160000.00,
        TRUE,
        TIMESTAMP '2026-08-08 09:00:00',
        TIMESTAMP '2026-08-08 12:00:00'
    ),
    (
        (SELECT id FROM users WHERE email = 'nurai.bekova@attractor.com'),
        'Frontend TypeScript Developer',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        120000.00,
        TRUE,
        TIMESTAMP '2026-08-09 09:00:00',
        TIMESTAMP '2026-08-09 12:00:00'
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
        'Java Developer at Nomad Tech',
        'Разработка backend-сервисов на Java и Spring Boot.',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        130000.00,
        1,
        3,
        TRUE,
        (SELECT id FROM users WHERE email = 'nomad.tech@attractor.com'),
        TIMESTAMP '2026-07-27 10:00:00',
        TIMESTAMP '2026-07-27 13:00:00'
    ),
    (
        'React Developer at Ala-Too Digital',
        'Разработка пользовательских интерфейсов на React.',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        115000.00,
        1,
        3,
        TRUE,
        (SELECT id FROM users WHERE email = 'alatoo.digital@attractor.com'),
        TIMESTAMP '2026-07-28 10:00:00',
        TIMESTAMP '2026-07-28 13:00:00'
    ),
    (
        'UI UX Designer at Bishkek Soft',
        'Проектирование интерфейсов и создание дизайн-систем.',
        (SELECT id FROM categories WHERE name = 'UI/UX Design'),
        100000.00,
        1,
        4,
        TRUE,
        (SELECT id FROM users WHERE email = 'bishkek.soft@attractor.com'),
        TIMESTAMP '2026-07-29 10:00:00',
        TIMESTAMP '2026-07-29 13:00:00'
    ),
    (
        'Junior Java Developer at Tunduk Labs',
        'Разработка REST API и поддержка Spring Boot приложений.',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        80000.00,
        0,
        2,
        TRUE,
        (SELECT id FROM users WHERE email = 'tunduk.labs@attractor.com'),
        TIMESTAMP '2026-07-30 10:00:00',
        TIMESTAMP '2026-07-30 13:00:00'
    ),
    (
        'Backend Engineer at Manas Systems',
        'Разработка сервисов, интеграций и работа с базами данных.',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        170000.00,
        2,
        5,
        TRUE,
        (SELECT id FROM users WHERE email = 'manas.systems@attractor.com'),
        TIMESTAMP '2026-07-31 10:00:00',
        TIMESTAMP '2026-07-31 13:00:00'
    ),
    (
        'Frontend Developer at Silk Road IT',
        'Разработка SPA на JavaScript и современных frontend технологиях.',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        120000.00,
        1,
        4,
        TRUE,
        (SELECT id FROM users WHERE email = 'silkroad.it@attractor.com'),
        TIMESTAMP '2026-08-01 10:00:00',
        TIMESTAMP '2026-08-01 13:00:00'
    ),
    (
        'Spring Developer at Kyrgyz Code',
        'Разработка корпоративных приложений на Java и Spring.',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        155000.00,
        2,
        4,
        TRUE,
        (SELECT id FROM users WHERE email = 'kyrgyz.code@attractor.com'),
        TIMESTAMP '2026-08-02 10:00:00',
        TIMESTAMP '2026-08-02 13:00:00'
    ),
    (
        'Product Designer at Peak Solutions',
        'Создание пользовательских сценариев, прототипов и UI макетов.',
        (SELECT id FROM categories WHERE name = 'UI/UX Design'),
        125000.00,
        2,
        5,
        TRUE,
        (SELECT id FROM users WHERE email = 'peak.solutions@attractor.com'),
        TIMESTAMP '2026-08-03 10:00:00',
        TIMESTAMP '2026-08-03 13:00:00'
    ),
    (
        'Senior Java Developer at Ak-Keme Digital',
        'Проектирование и разработка высоконагруженных Java сервисов.',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        220000.00,
        4,
        7,
        TRUE,
        (SELECT id FROM users WHERE email = 'akkeme.digital@attractor.com'),
        TIMESTAMP '2026-08-04 10:00:00',
        TIMESTAMP '2026-08-04 13:00:00'
    ),
    (
        'TypeScript Developer at Issyk-Kul Tech',
        'Разработка frontend приложений на TypeScript.',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        135000.00,
        2,
        5,
        TRUE,
        (SELECT id FROM users WHERE email = 'issykul.tech@attractor.com'),
        TIMESTAMP '2026-08-05 10:00:00',
        TIMESTAMP '2026-08-05 13:00:00'
    ),
    (
        'Java Software Engineer at Ordo Systems',
        'Разработка backend модулей и интеграционных сервисов.',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        180000.00,
        3,
        6,
        TRUE,
        (SELECT id FROM users WHERE email = 'ordo.systems@attractor.com'),
        TIMESTAMP '2026-08-06 10:00:00',
        TIMESTAMP '2026-08-06 13:00:00'
    ),
    (
        'UX Designer at Berkut Software',
        'Исследование пользователей и разработка интерфейсов продукта.',
        (SELECT id FROM categories WHERE name = 'UI/UX Design'),
        110000.00,
        1,
        4,
        TRUE,
        (SELECT id FROM users WHERE email = 'berkut.software@attractor.com'),
        TIMESTAMP '2026-08-07 10:00:00',
        TIMESTAMP '2026-08-07 13:00:00'
    ),
    (
        'Middle Java Developer at Jeti-Oguz Labs',
        'Разработка Spring Boot API и работа с SQL базами данных.',
        (SELECT id FROM categories WHERE name = 'Backend Development'),
        160000.00,
        2,
        5,
        TRUE,
        (SELECT id FROM users WHERE email = 'jetioguz.labs@attractor.com'),
        TIMESTAMP '2026-08-08 10:00:00',
        TIMESTAMP '2026-08-08 13:00:00'
    ),
    (
        'Frontend Engineer at Central Asia Dev',
        'Разработка и поддержка клиентской части веб-приложений.',
        (SELECT id FROM categories WHERE name = 'Frontend Development'),
        145000.00,
        2,
        5,
        TRUE,
        (SELECT id FROM users WHERE email = 'centralasia.dev@attractor.com'),
        TIMESTAMP '2026-08-09 10:00:00',
        TIMESTAMP '2026-08-09 13:00:00'
    );