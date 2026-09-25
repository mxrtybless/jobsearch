<#import "../layout.ftlh" as layout>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=resume.name>
    <h1>${resume.name}</h1>
    <h2 class="h4">${resume.applicant.name} ${resume.applicant.surname!}</h2>
    <p>${resume.category.name} · ${resume.salary!}</p>
    <h2 class="h4">${en?then("Work experience", "Опыт работы")}</h2>
    <#list resume.workExperienceInfo as item>
        <section class="card card-body mb-3"><h3 class="h5">${item.companyName} — ${item.position}</h3>
            <p>${en?then("Years", "Лет")}: ${item.years!0}</p><p style="white-space: pre-wrap">${item.responsibilities!}</p></section>
    <#else><p>—</p></#list>
    <h2 class="h4">${en?then("Education", "Образование")}</h2>
    <#list resume.educationInfo as item>
        <section class="card card-body mb-3"><h3 class="h5">${item.institution}</h3>
            <p>${item.program} · ${item.degree!}</p><p>${item.startDate!} — ${item.endDate!}</p></section>
    <#else><p>—</p></#list>
    <#list resume.contactInfo as contact><#if contact.value?has_content><p>${contact.type.type}: ${contact.value}</p></#if></#list>
    <#if ownVacancies?has_content && resume.published>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#invite-modal">${en?then("Start a discussion", "Пригласить к обсуждению")}</button>
        <div class="modal fade" id="invite-modal" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
            <form action="/responses/open" method="post">
                <div class="modal-header"><h2 class="modal-title fs-5">${en?then("Select a vacancy", "Выберите вакансию")}</h2><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>
                <div class="modal-body"><@layout.csrfInput/><input type="hidden" name="resumeId" value="${resume.id?c}">
                    <select name="vacancyId" class="form-select" required><#list ownVacancies as vacancy><option value="${vacancy.id?c}">${vacancy.name}</option></#list></select></div>
                <div class="modal-footer"><button class="btn btn-primary">${en?then("Continue", "Продолжить")}</button></div>
            </form>
        </div></div></div>
    <#elseif ownVacancies?size == 0>
        <p class="text-secondary">${en?then("An employer needs an active vacancy to start a discussion.", "Для приглашения работодателю нужна активная вакансия.")}</p>
    </#if>
</@layout.layout>
