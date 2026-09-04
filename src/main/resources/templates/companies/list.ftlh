<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "company.list.title"
        )>

<@layout.layout title=pageTitle + " | JobSearch">

    <h1 class="h2 mb-4">
        <@spring.message 'company.list.heading'/>
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
                                href="/companies/${company.id?c}">

                            <@spring.message 'company.list.details'/>

                        </a>

                    </div>

                </div>

            </#list>

        </div>

    <#else>

        <div class="alert alert-light">
            <@spring.message 'company.list.empty'/>
        </div>

    </#if>


    <@layout.pagination
            currentPage=currentPage!1
            totalPages=totalPages!0
            baseUrl="/companies"
            sort="date"/>

</@layout.layout>