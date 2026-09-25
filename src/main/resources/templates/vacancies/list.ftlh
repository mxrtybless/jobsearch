<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=en?then("Vacancies", "Вакансии")>
    <h1 class="h2 mb-4"><@spring.message 'vacancy.list.heading'/></h1>
    <form id="vacancy-filter-form" action="/vacancies" method="get" class="card card-body border-0 shadow-sm mb-4">
        <div class="row g-3">
            <div class="col-md-6">
                <label class="form-label" for="filter-name"><@spring.message 'vacancy.form.name'/></label>
                <input id="filter-name" name="name" type="search" class="form-control" maxlength="150" value="${filter.name!}">
            </div>
            <div class="col-md-6">
                <label class="form-label" for="filter-category"><@spring.message 'vacancy.form.category'/></label>
                <select id="filter-category" name="category" class="form-select">
                    <option value="">${en?then("All categories", "Все категории")}</option>
                    <#list categories as category>
                        <option value="${category.id?c}" <#if filter.category?? && filter.category == category.id>selected</#if>>${category.name}</option>
                    </#list>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label" for="filter-salary">${en?then("Minimum salary", "Минимальная зарплата")}</label>
                <input id="filter-salary" name="salary" type="number" min="0" step="0.01" class="form-control" value="<#if filter.salary??>${filter.salary?c}</#if>">
            </div>
            <div class="col-md-4">
                <label class="form-label" for="filter-experience">${en?then("Your experience, years", "Ваш опыт, лет")}</label>
                <input id="filter-experience" name="experience" type="number" min="0" max="70" step="1" class="form-control" value="<#if filter.experience??>${filter.experience?c}</#if>">
            </div>
            <div class="col-md-4">
                <label class="form-label" for="filter-sort">${en?then("Sort", "Сортировка")}</label>
                <select id="filter-sort" name="sort" class="form-select">
                    <#list ["dateDesc", "dateAsc", "responsesDesc", "responsesAsc"] as order>
                        <option value="${order}" <#if (filter.sort!"dateDesc") == order>selected</#if>><@spring.message ('vacancy.sort.' + order)/></option>
                    </#list>
                    <option value="salaryAsc" <#if (filter.sort!"") == "salaryAsc">selected</#if>>${en?then("Salary: low to high", "Зарплата: по возрастанию")}</option>
                    <option value="salaryDesc" <#if (filter.sort!"") == "salaryDesc">selected</#if>>${en?then("Salary: high to low", "Зарплата: по убыванию")}</option>
                </select>
            </div>
        </div>
        <div class="d-flex gap-2 mt-3">
            <button class="btn btn-primary" type="submit">${en?then("Search", "Найти")}</button>
            <button class="btn btn-outline-secondary" type="button" id="vacancy-filter-reset">${en?then("Clear", "Очистить")}</button>
        </div>
    </form>
    <p id="search-error" class="alert alert-danger d-none" role="alert">${en?then("Search failed. Please try again.", "Не удалось загрузить вакансии. Повторите поиск.")}</p>
    <div id="vacancy-results" aria-live="polite"><#include "results.ftlh"></div>
    <script src="/js/vacancy-filter.js" defer></script>
</@layout.layout>
