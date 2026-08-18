<#import "../layout.ftlh" as layout>
<@layout.layout title="Р’Р°РєР°РЅСЃРёРё | JobSearch">
<div class="d-flex flex-column flex-md-row justify-content-between align-items-md-end gap-3 mb-4">
    <div><h1 class="h2 mb-2">Р’Р°РєР°РЅСЃРёРё</h1><p class="text-secondary mb-0">РђРєС‚СѓР°Р»СЊРЅС‹Рµ РІР°РєР°РЅСЃРёРё СЂР°Р±РѕС‚РѕРґР°С‚РµР»РµР№</p></div>
    <form method="get" action="/vacancies" class="d-flex gap-2">
        <select class="form-select" name="sort" onchange="this.form.submit()">
            <option value="date" <#if sort!"date" == "date">selected</#if>>РџРѕ РґР°С‚Рµ РїСѓР±Р»РёРєР°С†РёРё</option>
            <option value="responses" <#if sort!"date" == "responses">selected</#if>>РџРѕ РєРѕР»РёС‡РµСЃС‚РІСѓ РѕС‚РєР»РёРєРѕРІ</option>
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
                        <p class="mb-2"><strong>Р—Р°СЂРїР»Р°С‚Р°:</strong> ${vacancy.salary!}</p>
                        <p class="mb-2"><strong>РћРїС‹С‚:</strong> ${vacancy.expFrom!0} вЂ” ${vacancy.expTo!0} Р»РµС‚</p>
                        <p class="mb-0 text-secondary">${vacancy.description!}</p>
                    </div>
                    <div class="text-secondary small text-end">РћР±РЅРѕРІР»РµРЅРѕ<br><strong class="text-dark"><#if vacancy.updateTime??>${vacancy.updateTime}<#elseif vacancy.createdDate??>${vacancy.createdDate}<#else>вЂ”</#if></strong></div>
                </div>
            </div></div>
        </#list>
    </div>
<#else>
    <div class="alert alert-light">РђРєС‚РёРІРЅС‹С… РІР°РєР°РЅСЃРёР№ РїРѕРєР° РЅРµС‚.</div>
</#if>
<@layout.pagination currentPage=currentPage!1 totalPages=totalPages!0 baseUrl="/vacancies" sort=sort!"date"/>
</@layout.layout>
