<#import "spring.ftl" as spring>

<#macro csrfInput>
    <#if _csrf??>
        <input
                type="hidden"
                name="${(_csrf.parameterName)!'csrf-param-name'}"
                value="${(_csrf.token)!'csrf-token'}">
    </#if>
</#macro>


<#macro layout title="JobSearch">
<!doctype html>

<html lang="ru">

<head>

    <meta charset="UTF-8">

    <meta
            name="viewport"
            content="width=device-width, initial-scale=1.0">

    <title>
        ${title}
    </title>

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

<#assign authenticated =
        SPRING_SECURITY_CONTEXT??
        && SPRING_SECURITY_CONTEXT.authentication??>

<#assign isEmployer = false>
<#assign isApplicant = false>


<#if authenticated>

    <#assign authentication =
            SPRING_SECURITY_CONTEXT.authentication>

    <#assign securityUser =
            authentication.principal>


    <#list authentication.authorities as authority>

        <#if authority?string == "EMPLOYER"
        || authority?string == "ROLE_EMPLOYER">

            <#assign isEmployer = true>

        </#if>


        <#if authority?string == "APPLICANT"
        || authority?string == "ROLE_APPLICANT">

            <#assign isApplicant = true>

        </#if>

    </#list>

</#if>


<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">

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
                aria-label="Toggle navigation">

            <span class="navbar-toggler-icon"></span>

        </button>


        <div
                class="collapse navbar-collapse"
                id="navbarContent">


            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">

                    <a
                            class="nav-link"
                            href="/vacancies">

                        <@spring.message
                                'layout.nav.vacancies'/>

                    </a>

                </li>


                <#if authenticated && isEmployer>

                    <li class="nav-item">

                        <a
                                class="nav-link"
                                href="/resumes">

                            <@spring.message
                                    'layout.nav.resumes'/>

                        </a>

                    </li>

                </#if>


                <#if authenticated && isApplicant>

                    <li class="nav-item">

                        <a
                                class="nav-link"
                                href="/companies">

                            <@spring.message
                                    'layout.nav.companies'/>

                        </a>

                    </li>

                </#if>

            </ul>


            <div class="d-flex flex-column flex-lg-row align-items-lg-center gap-2">


                <div class="dropdown">

                    <button
                            class="btn btn-outline-light btn-sm dropdown-toggle"
                            type="button"
                            data-bs-toggle="dropdown"
                            aria-expanded="false">

                        <@spring.message
                                'layout.language.change'/>

                    </button>


                    <ul class="dropdown-menu dropdown-menu-end">

                        <li>

                            <a
                                    class="dropdown-item"
                                    href="?lang=ru">

                                <@spring.message
                                        'layout.language.ru'/>

                            </a>

                        </li>


                        <li>

                            <a
                                    class="dropdown-item"
                                    href="?lang=en">

                                <@spring.message
                                        'layout.language.en'/>

                            </a>

                        </li>

                    </ul>

                </div>


                <#if authenticated>

                    <div class="d-flex align-items-center gap-2">

                        <span
                                class="text-white small d-none d-lg-inline">

                            ${securityUser.username}

                        </span>


                        <a
                                class="btn btn-light btn-sm"
                                href="/profile">

                            <@spring.message
                                    'layout.nav.profile'/>

                        </a>


                        <form
                                action="/auth/logout"
                                method="post"
                                class="m-0">

                            <@csrfInput/>


                            <button
                                    class="btn btn-outline-light btn-sm"
                                    type="submit">

                                <@spring.message
                                        'layout.nav.logout'/>

                            </button>

                        </form>

                    </div>


                <#else>

                    <div class="d-flex gap-2">

                        <a
                                class="btn btn-light btn-sm"
                                href="/auth/login">

                            <@spring.message
                                    'layout.nav.login'/>

                        </a>


                        <a
                                class="btn btn-outline-light btn-sm"
                                href="/auth/register">

                            <@spring.message
                                    'layout.nav.register'/>

                        </a>

                    </div>

                </#if>

            </div>

        </div>

    </div>

</nav>


<main class="container pb-5">

    <#nested>

</main>


</body>

</html>

</#macro>



<#macro
        pagination
        currentPage
        totalPages
        baseUrl
        sort="dateDesc">

    <#if totalPages?? && totalPages gt 1>

        <nav class="mt-4">

            <ul class="pagination justify-content-center">


                <#if currentPage gt 1>

                    <li class="page-item">

                        <a
                                class="page-link"
                                href="${baseUrl}?page=${currentPage - 1}&sort=${sort}">

                            <@spring.message
                                    'pagination.previous'/>

                        </a>

                    </li>

                </#if>


                <#list 1..totalPages as number>

                    <li
                            class="page-item <#if number == currentPage>active</#if>">

                        <a
                                class="page-link"
                                href="${baseUrl}?page=${number}&sort=${sort}">

                            ${number}

                        </a>

                    </li>

                </#list>


                <#if currentPage lt totalPages>

                    <li class="page-item">

                        <a
                                class="page-link"
                                href="${baseUrl}?page=${currentPage + 1}&sort=${sort}">

                            <@spring.message
                                    'pagination.next'/>

                        </a>

                    </li>

                </#if>

            </ul>

        </nav>

    </#if>

</#macro>