<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.layout title="${vacancy.name!} | JobSearch">

    <div class="card border-0 shadow-sm">

        <div class="card-body p-4">

            <h1 class="h2 mb-3">
                ${vacancy.name!}
            </h1>


            <p>

                <strong>
                    <@spring.message 'vacancy.details.author'/>:
                </strong>

                ${author.name!}

                <#if author.surname?? && author.surname?has_content>
                    ${author.surname}
                </#if>

            </p>


            <p>

                <strong>
                    <@spring.message 'vacancy.salary'/>:
                </strong>

                ${vacancy.salary!}

            </p>


            <p>

                <strong>
                    <@spring.message 'vacancy.details.category'/>:
                </strong>

                ${vacancy.categoryId!}

            </p>


            <p>

                <strong>
                    <@spring.message 'vacancy.experience'/>:
                </strong>

                ${vacancy.expFrom!0}
                —
                ${vacancy.expTo!0}

                <@spring.message 'vacancy.years'/>

            </p>


            <p>

                <strong>
                    <@spring.message 'vacancy.details.description'/>:
                </strong>

                <br>

                ${vacancy.description!}

            </p>


            <p class="text-secondary mb-0">

                <strong>
                    <@spring.message 'vacancy.details.published'/>:
                </strong>

                ${vacancy.createdDate!"—"}

            </p>

        </div>

    </div>

</@layout.layout>