<#macro layout title="JobSearch">
    <!doctype html>
    <html lang="ru">

    <head>
        <meta charset="UTF-8">

        <meta
                name="viewport"
                content="width=device-width, initial-scale=1.0">

        <title>${title}</title>

        <link
                href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
                rel="stylesheet">

        <link
                href="/css/style.css"
                rel="stylesheet">

        <script
                src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js">
        </script>
    </head>

    <body>

    <nav
            class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">

        <div class="container">

            <a
                    class="navbar-brand fw-bold"
                    href="/vacancies">
                JobSearch
            </a>

            <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarContent"
                    aria-controls="navbarContent"
                    aria-expanded="false"
                    aria-label="Открыть меню">

                <span class="navbar-toggler-icon"></span>

            </button>

            <div
                    class="collapse navbar-collapse"
                    id="navbarContent">

                <ul
                        class="navbar-nav me-auto mb-2 mb-lg-0">

                    <li class="nav-item">

                        <a
                                class="nav-link"
                                href="/vacancies">
                            Вакансии
                        </a>

                    </li>

                    <li class="nav-item">

                        <a
                                class="nav-link"
                                href="/resumes">
                            Резюме
                        </a>

                    </li>

                </ul>

                <#assign authenticated =
                        SPRING_SECURITY_CONTEXT??>

                <#if authenticated>

                    <#assign securityUser =
                            SPRING_SECURITY_CONTEXT
                                    .authentication
                                    .principal>

                    <div
                            class="d-flex align-items-center gap-2">

                        <span class="text-white small">
                            ${securityUser.username}
                        </span>

                        <a
                                class="btn btn-light btn-sm"
                                href="/profile">
                            Личный кабинет
                        </a>

                        <a
                                class="btn btn-outline-light btn-sm"
                                href="/auth/logout">
                            Выйти
                        </a>

                    </div>

                <#else>

                    <div class="d-flex gap-2">

                        <a
                                class="btn btn-light btn-sm"
                                href="/auth/login">
                            Войти
                        </a>

                        <a
                                class="btn btn-outline-light btn-sm"
                                href="/auth/register">
                            Регистрация
                        </a>

                    </div>

                </#if>

            </div>

        </div>

    </nav>

    <main class="container pb-5">

        <#nested>

    </main>

    </body>

    </html>
</#macro>