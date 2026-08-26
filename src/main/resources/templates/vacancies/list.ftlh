<#import "../layout.ftlh" as layout>
<@layout.layout title="Вакансии | JobSearch">
<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-end gap-3 mb-4">
    <div><h1 class="h2 mb-2">Вакансии</h1><p class="text-secondary mb-0">Актуальные вакансии работодателей</p></div>
    <form method="get" action="/vacancies" class="d-flex gap-2">
        <select class="form-select" name="sort" onchange="this.form.submit()">
            <option value="dateDesc" <#if (sort!"dateDesc") == "dateDesc">selected</#if>>По дате — сначала новые</option>
            <option value="dateAsc" <#if (sort!"dateDesc") == "dateAsc">selected</#if>>По дате — сначала старые</option>
            <option value="responsesDesc" <#if (sort!"dateDesc") == "responsesDesc">selected</#if>>По откликам — по убыванию</option>
            <option value="responsesAsc" <#if (sort!"dateDesc") == "responsesAsc">selected</#if>>По откликам — по возрастанию</option>
        </select>
        <input type="hidden" name="page" value="1">
    </form>
</div>
<#if vacancies?? && vacancies?size gt 0>
    <div class="d-flex flex-column gap-3">
        <#list vacancies as vacancy>
            <div class="card border-0 shadow-sm"><div class="card-body p-4">
                <div class="d-flex justify-content-between gap-3">
                    <div>
                        <h2 class="h4"><a class="text-decoration-none" href="/vacancies/${vacancy.id}">${vacancy.name!}</a></h2>
                        <p class="mb-2"><strong>Зарплата:</strong> ${vacancy.salary!}</p>
                        <p class="mb-2"><strong>Опыт:</strong> ${vacancy.expFrom!0} — ${vacancy.expTo!0} лет</p>
                        <p class="mb-0 text-secondary">${vacancy.description!}</p>
                    </div>
                    <div class="text-secondary small text-end">Обновлено<br><strong class="text-dark"><#if vacancy.updateTime??>${vacancy.updateTime}<#elseif vacancy.createdDate??>${vacancy.createdDate}<#else>—</#if></strong></div>
                </div>
            </div></div>
        </#list>
    </div>
<#else>
    <div class="alert alert-light">Активных вакансий пока нет.</div>
</#if>
<@layout.pagination currentPage=currentPage!1 totalPages=totalPages!0 baseUrl="/vacancies" sort=sort!"dateDesc"/>
</@layout.layout>
