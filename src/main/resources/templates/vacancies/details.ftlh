<#import "../layout.ftlh" as layout>
<@layout.layout title="${vacancy.name!} | JobSearch">
<div class="card border-0 shadow-sm"><div class="card-body p-4">
    <h1 class="h2 mb-3">${vacancy.name!}</h1>
    <p><strong>Автор / компания:</strong> ${author.name!} ${author.surname!}</p>
    <p><strong>Зарплата:</strong> ${vacancy.salary!}</p>
    <p><strong>Категория:</strong> ${vacancy.categoryId!}</p>
    <p><strong>Опыт:</strong> ${vacancy.expFrom!0} — ${vacancy.expTo!0} лет</p>
    <p><strong>Описание:</strong><br>${vacancy.description!}</p>
    <p class="text-secondary mb-0"><strong>Дата публикации:</strong> ${vacancy.createdDate!"—"}</p>
</div></div>

    <#if ownResumes?has_content && vacancy.isActive!false>
        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#response-modal">${.locale?starts_with("en")?then("Apply", "Откликнуться")}</button>
        <div class="modal fade" id="response-modal" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
            <form action="/responses/open" method="post">
                <div class="modal-header"><h2 class="modal-title fs-5">${.locale?starts_with("en")?then("Select a resume", "Выберите резюме")}</h2><button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>
                <div class="modal-body"><@layout.csrfInput/><input type="hidden" name="vacancyId" value="${vacancy.id?c}">
                    <select name="resumeId" class="form-select" required><#list ownResumes as resume><option value="${resume.id?c}">${resume.name}</option></#list></select></div>
                <div class="modal-footer"><button class="btn btn-primary">${.locale?starts_with("en")?then("Apply", "Откликнуться")}</button></div>
            </form>
        </div></div></div>
    <#else><p>${.locale?starts_with("en")?then("Sign in as an applicant and publish a resume to apply.", "Для отклика войдите как соискатель и опубликуйте резюме.")}</p></#if>
</@layout.layout>
