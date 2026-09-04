<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "vacancy.list.title"
        )>

<@layout.layout title=pageTitle + " | JobSearch">

    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-end gap-3 mb-4">

        <div>

            <h1 class="h2 mb-2">
                <@spring.message 'vacancy.list.heading'/>
            </h1>

            <p class="text-secondary mb-0">
                <@spring.message 'vacancy.list.description'/>
            </p>

        </div>


        <form
                method="get"
                action="/vacancies"
                class="d-flex gap-2">

            <select
                    class="form-select"
                    name="sort"
                    onchange="this.form.submit()">

                <option
                        value="dateDesc"
                        <#if (sort!"dateDesc") == "dateDesc">
                            selected
                        </#if>>
                    <@spring.message 'vacancy.sort.dateDesc'/>
                </option>

                <option
                        value="dateAsc"
                        <#if (sort!"dateDesc") == "dateAsc">
                            selected
                        </#if>>
                    <@spring.message 'vacancy.sort.dateAsc'/>
                </option>

                <option
                        value="responsesDesc"
                        <#if (sort!"dateDesc") == "responsesDesc">
                            selected
                        </#if>>
                    <@spring.message 'vacancy.sort.responsesDesc'/>
                </option>

                <option
                        value="responsesAsc"
                        <#if (sort!"dateDesc") == "responsesAsc">
                            selected
                        </#if>>
                    <@spring.message 'vacancy.sort.responsesAsc'/>
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

                    <div class="card-body p-4">

                        <div class="d-flex justify-content-between gap-3">

                            <div>

                                <h2 class="h4">

                                    <a
                                            class="text-decoration-none"
                                            href="/vacancies/${vacancy.id?c}">
                                        ${vacancy.name!}
                                    </a>

                                </h2>

                                <p class="mb-2">

                                    <strong>
                                        <@spring.message 'vacancy.salary'/>:
                                    </strong>

                                    ${vacancy.salary!}

                                </p>

                                <p class="mb-2">

                                    <strong>
                                        <@spring.message 'vacancy.experience'/>:
                                    </strong>

                                    ${vacancy.expFrom!0}
                                    —
                                    ${vacancy.expTo!0}

                                    <@spring.message 'vacancy.years'/>

                                </p>

                                <p class="mb-0 text-secondary">
                                    ${vacancy.description!}
                                </p>

                            </div>


                            <div class="text-secondary small text-end">

                                <@spring.message 'vacancy.updated'/>

                                <br>

                                <strong class="text-dark">

                                    <#if vacancy.updateTime??>
                                        ${vacancy.updateTime}
                                    <#elseif vacancy.createdDate??>
                                        ${vacancy.createdDate}
                                    <#else>
                                        —
                                    </#if>

                                </strong>

                            </div>

                        </div>

                    </div>

                </div>

            </#list>

        </div>


    <#else>

        <div class="alert alert-light">
            <@spring.message 'vacancy.list.empty'/>
        </div>

    </#if>


    <@layout.pagination
            currentPage=currentPage!1
            totalPages=totalPages!0
            baseUrl="/vacancies"
            sort=sort!"dateDesc"/>

</@layout.layout>