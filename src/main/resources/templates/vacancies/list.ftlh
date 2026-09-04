<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "resume.list.title"
        )>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-end gap-3 mb-4">

        <div>

            <h1 class="h2 mb-2">
                <@spring.message 'resume.list.heading'/>
            </h1>

            <p class="text-secondary mb-0">
                <@spring.message 'resume.list.description'/>
            </p>

        </div>


        <form
                method="get"
                action="/resumes"
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
                    <@spring.message 'resume.sort.dateDesc'/>
                </option>

                <option
                        value="dateAsc"
                        <#if (sort!"dateDesc") == "dateAsc">
                            selected
                        </#if>>
                    <@spring.message 'resume.sort.dateAsc'/>
                </option>

                <option
                        value="responsesDesc"
                        <#if (sort!"dateDesc") == "responsesDesc">
                            selected
                        </#if>>
                    <@spring.message 'resume.sort.responsesDesc'/>
                </option>

                <option
                        value="responsesAsc"
                        <#if (sort!"dateDesc") == "responsesAsc">
                            selected
                        </#if>>
                    <@spring.message 'resume.sort.responsesAsc'/>
                </option>

            </select>

            <input
                    type="hidden"
                    name="page"
                    value="1">

        </form>

    </div>


    <#if resumes?? && resumes?size gt 0>

        <div class="d-flex flex-column gap-3">

            <#list resumes as resume>

                <div class="card border-0 shadow-sm">

                    <div class="card-body p-4">

                        <h2 class="h4">
                            ${resume.name!}
                        </h2>


                        <p class="mb-2">

                            <strong>
                                <@spring.message 'resume.salary'/>:
                            </strong>

                            ${resume.salary!}

                        </p>


                        <p class="mb-2">

                            <strong>
                                <@spring.message 'resume.category'/>:
                            </strong>

                            ${resume.categoryId!}

                        </p>


                        <p class="mb-0 text-secondary small">

                            <@spring.message 'resume.updated'/>:

                            <#if resume.updateTime??>
                                ${resume.updateTime}
                            <#elseif resume.createdDate??>
                                ${resume.createdDate}
                            <#else>
                                —
                            </#if>

                        </p>

                    </div>

                </div>

            </#list>

        </div>


    <#else>

        <div class="alert alert-light">
            <@spring.message 'resume.list.empty'/>
        </div>

    </#if>


    <@layout.pagination
            currentPage=currentPage!1
            totalPages=totalPages!0
            baseUrl="/resumes"
            sort=sort!"dateDesc"/>

</@layout.layout>