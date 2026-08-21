<#import "../layout.ftlh" as layout>
<@layout.layout title="Личный кабинет | JobSearch">
<div class="card border-0 shadow-sm mb-4"><div class="card-body p-4">
    <div class="d-flex flex-column flex-md-row gap-4 align-items-md-start">
        <#if user.avatar?? && user.avatar?has_content>
            <img src="/images/download?filename=${user.avatar?url}" alt="avatar" class="rounded-circle border" width="110" height="110" style="object-fit:cover">
        </#if>
        <div class="flex-grow-1">
            <h1 class="h3">${user.name!} ${user.surname!}</h1>
            <p class="mb-1"><strong>Email:</strong> ${user.email!}</p>
            <p class="mb-1"><strong>Телефон:</strong> ${user.phoneNumber!}</p>
            <#if user.age??><p><strong>Возраст:</strong> ${user.age}</p></#if>
            <div class="d-flex flex-wrap gap-2">
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editProfileModal">Редактировать</button>
                <form action="/profile/avatar" method="post" enctype="multipart/form-data" class="d-flex gap-2">
                    <@layout.csrfInput/>
                    <input class="form-control" type="file" name="avatar" accept="image/*" required>
                    <button class="btn btn-outline-primary" type="submit">Обновить фото</button>
                </form>
            </div>
        </div>
    </div>
</div></div>

<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-3">
    <div>
        <h2 class="h4 mb-1"><#if isApplicant>Мои резюме<#else>Мои вакансии</#if></h2>
    </div>
    <div class="d-flex gap-2">
        <form method="get" action="/profile">
            <select class="form-select" name="sort" onchange="this.form.submit()">
                <option value="date" <#if sort!"date" == "date">selected</#if>>По дате</option>
                <option value="responses" <#if sort!"date" == "responses">selected</#if>>По откликам</option>
            </select>
            <input type="hidden" name="page" value="1">
        </form>
        <#if isApplicant><a class="btn btn-primary" href="/resumes/form/create">Создать резюме</a></#if>
        <#if isEmployer><a class="btn btn-primary" href="/vacancies/form/create">Создать вакансию</a></#if>
    </div>
</div>

<#if isApplicant>
    <#if resumes?? && resumes?size gt 0><div class="row g-3">
        <#list resumes as resume><div class="col-12 col-md-6"><div class="card h-100 border-0 shadow-sm"><div class="card-body">
            <div class="d-flex justify-content-between"><h3 class="h5">${resume.name!}</h3><span class="badge <#if resume.isActive>text-bg-success<#else>text-bg-secondary</#if>"><#if resume.isActive>Опубликовано<#else>Скрыто</#if></span></div>
            <p><strong>Зарплата:</strong> ${resume.salary!}</p>
            <p class="small text-secondary">Обновлено: <#if resume.updateTime??>${resume.updateTime}<#elseif resume.createdDate??>${resume.createdDate}<#else>—</#if></p>
            <div class="d-flex gap-2"><a class="btn btn-outline-primary btn-sm" href="/resumes/form/edit/${resume.id}">Редактировать</a>
            <form method="post" action="/resumes/form/update/${resume.id}"><@layout.csrfInput/><button class="btn btn-outline-secondary btn-sm">Обновить дату</button></form></div>
        </div></div></div></#list>
    </div><#else><div class="alert alert-light">У вас пока нет резюме.</div></#if>
<#else>
    <#if vacancies?? && vacancies?size gt 0><div class="row g-3">
        <#list vacancies as vacancy><div class="col-12 col-md-6"><div class="card h-100 border-0 shadow-sm"><div class="card-body">
            <div class="d-flex justify-content-between"><h3 class="h5">${vacancy.name!}</h3><span class="badge <#if vacancy.isActive>text-bg-success<#else>text-bg-secondary</#if>"><#if vacancy.isActive>Опубликовано<#else>Скрыто</#if></span></div>
            <p><strong>Зарплата:</strong> ${vacancy.salary!}</p>
            <p class="small text-secondary">Обновлено: <#if vacancy.updateTime??>${vacancy.updateTime}<#elseif vacancy.createdDate??>${vacancy.createdDate}<#else>—</#if></p>
            <div class="d-flex gap-2"><a class="btn btn-outline-primary btn-sm" href="/vacancies/form/edit/${vacancy.id}">Редактировать</a>
            <form method="post" action="/vacancies/form/update/${vacancy.id}"><@layout.csrfInput/><button class="btn btn-outline-secondary btn-sm">Обновить дату</button></form></div>
        </div></div></div></#list>
    </div><#else><div class="alert alert-light">У вас пока нет вакансий.</div></#if>
</#if>
<@layout.pagination currentPage=currentPage!1 totalPages=totalPages!0 baseUrl="/profile" sort=sort!"date"/>

<div class="modal fade" id="editProfileModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
<form action="/profile/edit" method="post">
    <@layout.csrfInput/>
<div class="modal-header"><h5 class="modal-title">Редактирование профиля</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
<div class="modal-body">
    <div class="mb-3"><label class="form-label">Имя</label><input class="form-control" name="name" value="${user.name!}" required></div>
    <div class="mb-3"><label class="form-label">Фамилия</label><input class="form-control" name="surname" value="${user.surname!}"></div>
    <div class="mb-3"><label class="form-label">Возраст</label><input class="form-control" type="number" name="age" value="${user.age!}"></div>
</div>
<div class="modal-footer"><button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Отмена</button><button class="btn btn-primary" type="submit">Сохранить</button></div>
</form></div></div></div>
</@layout.layout>
