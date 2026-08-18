<#import "../layout.ftlh" as layout>
<@layout.layout title="Р РµР·СЋРјРµ | JobSearch">
<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-end gap-3 mb-4">
    <div><h1 class="h2 mb-2">Р РµР·СЋРјРµ СЃРѕРёСЃРєР°С‚РµР»РµР№</h1><p class="text-secondary mb-0">РћРїСѓР±Р»РёРєРѕРІР°РЅРЅС‹Рµ СЂРµР·СЋРјРµ</p></div>
    <form method="get" action="/resumes" class="d-flex gap-2">
        <select class="form-select" name="sort" onchange="this.form.submit()">
            <option value="date" <#if sort!"date" == "date">selected</#if>>РџРѕ РґР°С‚Рµ РїСѓР±Р»РёРєР°С†РёРё</option>
            <option value="responses" <#if sort!"date" == "responses">selected</#if>>РџРѕ РєРѕР»РёС‡РµСЃС‚РІСѓ РѕС‚РєР»РёРєРѕРІ</option>
        </select>
        <input type="hidden" name="page" value="1">
    </form>
</div>
<#if resumes?? && resumes?size gt 0>
    <div class="d-flex flex-column gap-3">
        <#list resumes as resume>
            <div class="card border-0 shadow-sm"><div class="card-body p-4">
                <h2 class="h4">${resume.name!}</h2>
                <p class="mb-2"><strong>Р–РµР»Р°РµРјР°СЏ Р·Р°СЂРїР»Р°С‚Р°:</strong> ${resume.salary!}</p>
                <p class="mb-2"><strong>РљР°С‚РµРіРѕСЂРёСЏ:</strong> ${resume.categoryId!}</p>
                <p class="mb-0 text-secondary small">РћР±РЅРѕРІР»РµРЅРѕ: <#if resume.updateTime??>${resume.updateTime}<#elseif resume.createdDate??>${resume.createdDate}<#else>вЂ”</#if></p>
            </div></div>
        </#list>
    </div>
<#else>
    <div class="alert alert-light">РђРєС‚РёРІРЅС‹С… СЂРµР·СЋРјРµ РїРѕРєР° РЅРµС‚.</div>
</#if>
<@layout.pagination currentPage=currentPage!1 totalPages=totalPages!0 baseUrl="/resumes" sort=sort!"date"/>
</@layout.layout>
