<#import "../layout.ftlh" as layout>
<@layout.layout title="Компания | JobSearch">
<div class="card border-0 shadow-sm mb-4"><div class="card-body p-4">
    <h1 class="h2">${company.name!}</h1>
    <p class="mb-1"><strong>Email:</strong> ${company.email!}</p>
    <p class="mb-0"><strong>Телефон:</strong> ${company.phoneNumber!}</p>
</div></div>
<div class="d-flex justify-content-between align-items-center mb-3"><h2 class="h4 mb-0">Вакансии компании</h2>
<form method="get" action="/companies/${company.id}"><select class="form-select" name="sort" onchange="this.form.submit()"><option value="date" <#if sort!"date" == "date">selected</#if>>По дате</option><option value="responses" <#if sort!"date" == "responses">selected</#if>>По откликам</option></select><input type="hidden" name="page" value="1"></form></div>
<#if vacancies?? && vacancies?size gt 0><div class="d-flex flex-column gap-3">
<#list vacancies as vacancy><div class="card border-0 shadow-sm"><div class="card-body"><h3 class="h5"><a href="/vacancies/${vacancy.id}" class="text-decoration-none">${vacancy.name!}</a></h3><p class="mb-0">${vacancy.salary!}</p></div></div></#list>
</div><#else><div class="alert alert-light">У компании нет опубликованных вакансий.</div></#if>
<@layout.pagination currentPage=currentPage!1 totalPages=totalPages!0 baseUrl="/companies/${company.id}" sort=sort!"date"/>
</@layout.layout>
