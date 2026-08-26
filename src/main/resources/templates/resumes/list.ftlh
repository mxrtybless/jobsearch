<#import "../layout.ftlh" as layout>
<@layout.layout title="Резюме | JobSearch">
<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-end gap-3 mb-4">
    <div><h1 class="h2 mb-2">Резюме соискателей</h1><p class="text-secondary mb-0">Опубликованные резюме</p></div>
    <form method="get" action="/resumes" class="d-flex gap-2">
        <select class="form-select" name="sort" onchange="this.form.submit()">
            <option value="dateDesc" <#if (sort!"dateDesc") == "dateDesc">selected</#if>>По дате — сначала новые</option>
            <option value="dateAsc" <#if (sort!"dateDesc") == "dateAsc">selected</#if>>По дате — сначала старые</option>
            <option value="responsesDesc" <#if (sort!"dateDesc") == "responsesDesc">selected</#if>>По откликам — по убыванию</option>
            <option value="responsesAsc" <#if (sort!"dateDesc") == "responsesAsc">selected</#if>>По откликам — по возрастанию</option>
        </select>
        <input type="hidden" name="page" value="1">
    </form>
</div>
<#if resumes?? && resumes?size gt 0>
    <div class="d-flex flex-column gap-3">
        <#list resumes as resume>
            <div class="card border-0 shadow-sm"><div class="card-body p-4">
                <h2 class="h4">${resume.name!}</h2>
                <p class="mb-2"><strong>Желаемая зарплата:</strong> ${resume.salary!}</p>
                <p class="mb-2"><strong>Категория:</strong> ${resume.categoryId!}</p>
                <p class="mb-0 text-secondary small">Обновлено: <#if resume.updateTime??>${resume.updateTime}<#elseif resume.createdDate??>${resume.createdDate}<#else>—</#if></p>
            </div></div>
        </#list>
    </div>
<#else>
    <div class="alert alert-light">Активных резюме пока нет.</div>
</#if>
<@layout.pagination currentPage=currentPage!1 totalPages=totalPages!0 baseUrl="/resumes" sort=sort!"dateDesc"/>
</@layout.layout>
