<#macro layout title="JobSearch">
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<#assign authenticated = SPRING_SECURITY_CONTEXT?? && SPRING_SECURITY_CONTEXT.authentication??>
<#assign isEmployer = false>
<#assign isApplicant = false>
<#if authenticated>
    <#assign authentication = SPRING_SECURITY_CONTEXT.authentication>
    <#assign securityUser = authentication.principal>
    <#list authentication.authorities as authority>
        <#if authority?string == "EMPLOYER" || authority?string == "ROLE_EMPLOYER">
            <#assign isEmployer = true>
        </#if>
        <#if authority?string == "APPLICANT" || authority?string == "ROLE_APPLICANT">
            <#assign isApplicant = true>
        </#if>
    </#list>
</#if>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold" href="/vacancies">JobSearch</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="/vacancies">Р’Р°РєР°РЅСЃРёРё</a></li>
                <#if authenticated && isEmployer>
                    <li class="nav-item"><a class="nav-link" href="/resumes">Р’СЃРµ СЂРµР·СЋРјРµ</a></li>
                </#if>
                <#if authenticated && isApplicant>
                    <li class="nav-item"><a class="nav-link" href="/companies">РљРѕРјРїР°РЅРёРё</a></li>
                </#if>
            </ul>
            <#if authenticated>
                <div class="d-flex align-items-center gap-2">
                    <span class="text-white small d-none d-lg-inline">${securityUser.username}</span>
                    <a class="btn btn-light btn-sm" href="/profile">Р›РёС‡РЅС‹Р№ РєР°Р±РёРЅРµС‚</a>
                    <a class="btn btn-outline-light btn-sm" href="/auth/logout">Р’С‹Р№С‚Рё</a>
                </div>
            <#else>
                <div class="d-flex gap-2">
                    <a class="btn btn-light btn-sm" href="/auth/login">Р’РѕР№С‚Рё</a>
                    <a class="btn btn-outline-light btn-sm" href="/auth/register">Р РµРіРёСЃС‚СЂР°С†РёСЏ</a>
                </div>
            </#if>
        </div>
    </div>
</nav>
<main class="container pb-5"><#nested></main>
</body>
</html>
</#macro>

<#macro pagination currentPage totalPages baseUrl sort="date">
    <#if totalPages?? && totalPages gt 1>
        <nav class="mt-4">
            <ul class="pagination justify-content-center">
                <#if currentPage gt 1>
                    <li class="page-item"><a class="page-link" href="${baseUrl}?page=${currentPage - 1}&sort=${sort}">РќР°Р·Р°Рґ</a></li>
                </#if>
                <#list 1..totalPages as number>
                    <li class="page-item <#if number == currentPage>active</#if>">
                        <a class="page-link" href="${baseUrl}?page=${number}&sort=${sort}">${number}</a>
                    </li>
                </#list>
                <#if currentPage lt totalPages>
                    <li class="page-item"><a class="page-link" href="${baseUrl}?page=${currentPage + 1}&sort=${sort}">Р’РїРµСЂС‘Рґ</a></li>
                </#if>
            </ul>
        </nav>
    </#if>
</#macro>
