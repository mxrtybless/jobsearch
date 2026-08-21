<#import "../layout.ftlh" as layout>
<@layout.layout title="Компании | JobSearch">
<h1 class="h2 mb-4">Компании</h1>
<#if companies?? && companies?size gt 0><div class="row g-3">
<#list companies as company><div class="col-12 col-md-6 col-lg-4"><div class="card h-100 border-0 shadow-sm"><div class="card-body">
    <h2 class="h5">${company.name!}</h2>
    <p class="mb-1">${company.email!}</p><p class="text-secondary">${company.phoneNumber!}</p>
    <a class="btn btn-outline-primary" href="/companies/${company.id}">Подробнее</a>
</div></div></div></#list></div><#else><div class="alert alert-light">Компании пока не найдены.</div></#if>
<@layout.pagination currentPage=currentPage!1 totalPages=totalPages!0 baseUrl="/companies" sort="date"/>
</@layout.layout>
