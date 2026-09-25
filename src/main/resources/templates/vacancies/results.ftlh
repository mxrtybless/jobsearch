<#import "/spring.ftl" as spring>
<#assign en = .locale?starts_with("en")>
<#if filterInvalid!false>
    <div class="alert alert-danger">${en?then("Check the filter values.", "Проверьте значения фильтра.")}</div>
<#else>
    <p class="text-secondary">${en?then("Found", "Найдено")}: ${totalElements?c}</p>
    <#list vacancies as vacancy>
        <article class="card card-body border-0 shadow-sm mb-3">
            <h2 class="h4"><a href="/vacancies/${vacancy.id?c}">${vacancy.name}</a></h2>
            <p><@spring.message 'vacancy.salary'/>: ${vacancy.salary!} · ${vacancy.categoryName}</p>
            <p><@spring.message 'vacancy.experience'/>: ${vacancy.expFrom!0}–${vacancy.expTo!0} <@spring.message 'vacancy.years'/></p>
            <p>${vacancy.description!}</p>
            <p><a href="/companies/${vacancy.authorId?c}">${vacancy.authorName}</a></p>
            <div class="text-secondary small"><@spring.message 'vacancy.details.published'/>: ${vacancy.createdDate!} · <@spring.message 'vacancy.updated'/>: ${vacancy.updateTime!}</div>
        </article>
    <#else>
        <div class="alert alert-light">${en?then("No matching vacancies.", "Подходящих вакансий нет.")}</div>
    </#list>
    <#assign categoryValue = "", salaryValue = "", experienceValue = "">
    <#if filter.category??><#assign categoryValue = filter.category?c></#if>
    <#if filter.salary??><#assign salaryValue = filter.salary?c></#if>
    <#if filter.experience??><#assign experienceValue = filter.experience?c></#if>
    <#assign query = "name=" + (filter.name!"")?url('UTF-8') + "&category=" + categoryValue + "&salary=" + salaryValue + "&experience=" + experienceValue + "&sort=" + (filter.sort!"dateDesc")?url('UTF-8')>
    <nav class="d-flex gap-3 align-items-center my-4">
        <#if currentPage gt 1><a class="btn btn-outline-primary" data-page="${(currentPage-1)?c}" href="/vacancies?${query}&page=${(currentPage-1)?c}"><@spring.message 'pagination.previous'/></a></#if>
        <span>${currentPage?c} / ${[totalPages, 1]?max?c}</span>
        <#if currentPage lt totalPages><a class="btn btn-outline-primary" data-page="${(currentPage+1)?c}" href="/vacancies?${query}&page=${(currentPage+1)?c}"><@spring.message 'pagination.next'/></a></#if>
    </nav>
</#if>
