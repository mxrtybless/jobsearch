<#import "../layout.ftlh" as layout>
<@layout.layout title="${vacancy.name!} | JobSearch">
<div class="card border-0 shadow-sm"><div class="card-body p-4">
    <h1 class="h2 mb-3">${vacancy.name!}</h1>
    <p><strong>РђРІС‚РѕСЂ / РєРѕРјРїР°РЅРёСЏ:</strong> ${author.name!} ${author.surname!}</p>
    <p><strong>Р—Р°СЂРїР»Р°С‚Р°:</strong> ${vacancy.salary!}</p>
    <p><strong>РљР°С‚РµРіРѕСЂРёСЏ:</strong> ${vacancy.categoryId!}</p>
    <p><strong>РћРїС‹С‚:</strong> ${vacancy.expFrom!0} вЂ” ${vacancy.expTo!0} Р»РµС‚</p>
    <p><strong>РћРїРёСЃР°РЅРёРµ:</strong><br>${vacancy.description!}</p>
    <p class="text-secondary mb-0"><strong>Р”Р°С‚Р° РїСѓР±Р»РёРєР°С†РёРё:</strong> ${vacancy.createdDate!"вЂ”"}</p>
</div></div>
</@layout.layout>
