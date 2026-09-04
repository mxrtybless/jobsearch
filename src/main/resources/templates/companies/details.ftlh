<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "company.details.title"
        )>

<@layout.layout title=pageTitle + " | JobSearch">

    <div class="card border-0 shadow-sm mb-4">

        <div class="card-body p-4">

            <h1 class="h2">
                ${company.name!}
            </h1>

            <p class="mb-1">

                <strong>
                    Email:
                </strong>

                ${company.email!}

            </p>

            <p class="mb-0">

                <strong>
                    <@spring.message 'company.details.phone'/>:
                </strong>

                ${company.phoneNumber!}

            </p>

        </div>

    </div>


    <div
            class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-3">

        <h2 class="h4 mb-0">
            <@spring.message 'company.details.vacancies'/>
        </h2>

        <form
                method="get"
                action="/companies/${company.id?c}">

            <select
                    class="form-select"
                    name="sort"
                    onchange="this.form.submit()">

                <option
                        value="dateDesc"
                        <#if (sort!"dateDesc") == "dateDesc">
                            selected
                        </#if>>
                    <@spring.message 'company.sort.dateDesc'/>
                </option>

                <option
                        value="dateAsc"
                        <#if (sort!"dateDesc") == "dateAsc">
                            selected
                        </#if>>
                    <@spring.message 'company.sort.dateAsc'/>
                </option>

                <option
                        value="responsesDesc"
                        <#if (sort!"dateDesc") == "responsesDesc">
                            selected
                        </#if>>
                    <@spring.message 'company.sort.responsesDesc'/>
                </option>

                <option
                        value="responsesAsc"
                        <#if (sort!"dateDesc") == "responsesAsc">
                            selected
                        </#if>>
                    <@spring.message 'company.sort.responsesAsc'/>
                </option>

            </select>

            <input
                    type="hidden"
                    name="page"
                    value="1">

        </form>

    </div>


    <#if vacancies?? && vacancies?size gt 0>

        <div class="d-flex flex-column gap-3">

            <#list vacancies as vacancy>

                <div class="card border-0 shadow-sm">

                    <div class="card-body">

                        <h3 class="h5">

                            <a
                                    href="/vacancies/${vacancy.id?c}"
                                    class="text-decoration-none">

                                ${vacancy.name!}

                            </a>

                        </h3>

                        <p class="mb-0">
                            ${vacancy.salary!}
                        </p>

                    </div>

                </div>

            </#list>

        </div>

    <#else>

        <div class="alert alert-light">
            <@spring.message 'company.details.empty'/>
        </div>

    </#if>


    <@layout.pagination
            currentPage=currentPage!1
            totalPages=totalPages!0
            baseUrl="/companies/${company.id?c}"
            sort=sort!"dateDesc"/>

</@layout.layout>