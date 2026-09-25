<#import "../layout.ftlh" as layout>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=en?then("Responses", "Отклики")>
    <h1>${en?then("Responses and invitations", "Отклики и приглашения")}</h1>
    <#if ownVacancies?has_content>
        <form class="d-flex gap-2 mb-3"><select name="vacancyId" class="form-select">
            <option value="">${en?then("All vacancies", "Все вакансии")}</option>
            <#list ownVacancies as item><option value="${item.id?c}" <#if vacancyId?? && vacancyId == item.id>selected</#if>>${item.name}</option></#list>
        </select><button class="btn btn-primary">${en?then("Show", "Показать")}</button></form>
    </#if>
    <#list responses as response>
        <article class="card card-body mb-3">
            <h2 class="h4">${response.vacancy.name}</h2>
            <p>${response.vacancy.author.name}</p>
            <a href="/resumes/${response.resume.id?c}">${response.resume.applicant.name} ${response.resume.applicant.surname!} — ${response.resume.name}</a>
            <#if layout.isEmployer><a href="/applicants/${response.resume.applicant.id?c}">${en?then("Applicant profile", "Профиль соискателя")}</a></#if>
            <a class="btn btn-outline-primary mt-2" href="/responses/${response.id?c}/chat">${en?then("Open chat", "Открыть переписку")}</a>
        </article>
    <#else><p>${en?then("No responses yet.", "Откликов пока нет.")}</p></#list>
</@layout.layout>
