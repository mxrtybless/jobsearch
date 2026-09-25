<#import "../layout.ftlh" as layout>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=en?then("Resumes", "Резюме")>
    <h1>${en?then("Resumes", "Резюме")}</h1>
    <form action="/resumes" class="d-flex gap-2 my-3">
        <select name="category" class="form-select" aria-label="${en?then('Category', 'Категория')}">
            <option value="">${en?then("All categories", "Все категории")}</option>
            <#list categories as item><option value="${item.id?c}" <#if category?? && category == item.id>selected</#if>>${item.name}</option></#list>
        </select>
        <select name="sort" class="form-select" aria-label="${en?then('Sort', 'Сортировка')}">
            <#list ["dateDesc", "dateAsc", "responsesDesc", "responsesAsc"] as order>
                <option value="${order}" <#if sort == order>selected</#if>>${springMacroRequestContext.getMessage("resume.sort." + order)}</option>
            </#list>
        </select>
        <button class="btn btn-primary">${en?then("Search", "Найти")}</button>
    </form>
    <#list resumes as resume>
        <article class="card card-body mb-3">
            <h2 class="h4"><a href="/resumes/${resume.id?c}">${resume.name}</a></h2>
            <p>${resume.applicant.name} ${resume.applicant.surname!}</p>
            <p>${resume.category.name} · ${resume.salary!}</p>
            <small>${resume.updateTime!}</small>
        </article>
    <#else><p>${en?then("No resumes found.", "Резюме не найдены.")}</p></#list>
    <nav class="d-flex gap-3">
        <#if currentPage gt 1><a href="/resumes?page=${(currentPage-1)?c}&sort=${sort?url('UTF-8')}&category=<#if category??>${category?c}</#if>">${en?then("Previous", "Назад")}</a></#if>
        <span>${currentPage?c} / ${[1,totalPages]?max?c}</span>
        <#if currentPage lt totalPages><a href="/resumes?page=${(currentPage+1)?c}&sort=${sort?url('UTF-8')}&category=<#if category??>${category?c}</#if>">${en?then("Next", "Вперёд")}</a></#if>
    </nav>
</@layout.layout>
