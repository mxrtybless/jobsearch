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
</@layout.layout>
