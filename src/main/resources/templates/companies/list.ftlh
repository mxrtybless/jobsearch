<#import "../layout.ftlh" as layout>

<@layout.layout title="Компании | JobSearch">

    <h1 class="h2 mb-4">
        Компании
    </h1>

    <#if companies?? && companies?size gt 0>

        <div class="d-grid gap-3">

            <#list companies as company>

                <div class="card border-0 shadow-sm">

                    <div class="card-body">

                        <h2 class="h5">
                            ${company.name!}
                        </h2>

                        <p class="mb-1">
                            ${company.email!}
                        </p>

                        <p class="text-secondary">
                            ${company.phoneNumber!}
                        </p>

                        <a
                                class="btn btn-outline-primary"
                                href="/companies/${company.id}">
                            Подробнее
                        </a>

                    </div>

                </div>

            </#list>

        </div>

    <#else>

        <div class="alert alert-light">
            Компании пока не найдены.
        </div>

    </#if>

    <@layout.pagination
            currentPage=currentPage!1
            totalPages=totalPages!0
            baseUrl="/companies"
            sort="date"/>

</@layout.layout>