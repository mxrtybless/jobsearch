<#import "../layout.ftlh" as layout>

<@layout.layout title="Резюме | JobSearch">

    <div class="mb-4">

        <h1 class="h2 mb-2">
            Резюме соискателей
        </h1>

        <p class="text-secondary mb-0">
            Опубликованные резюме соискателей
            JobSearch
        </p>

    </div>


    <#if resumes?? && resumes?size gt 0>

        <div class="d-flex flex-column gap-3">

            <#list resumes as resume>

                <div
                        class="card
                               border-0
                               shadow-sm
                               list-card">

                    <div class="card-body p-4">

                        <div
                                class="d-flex
                                       flex-column
                                       flex-md-row
                                       justify-content-between
                                       gap-3">

                            <div>

                                <h2 class="h4 mb-3">
                                    ${resume.name!}
                                </h2>

                                <div class="entity-meta">

                                    <div>

                                        <span
                                                class="text-secondary">
                                            Ожидаемая зарплата:
                                        </span>

                                        <strong>
                                            ${resume.salary!}
                                        </strong>

                                    </div>

                                    <div>

                                        <span
                                                class="text-secondary">
                                            Категория:
                                        </span>

                                        <strong>
                                            ${resume.categoryId!}
                                        </strong>

                                    </div>

                                </div>

                            </div>


                            <div
                                    class="text-md-end
                                           text-secondary
                                           small">

                                <div class="mb-1">
                                    Обновлено
                                </div>

                                <strong
                                        class="text-dark">

                                    <#if resume.updateTime??>

                                        ${resume.updateTime}

                                    <#elseif resume.createdDate??>

                                        ${resume.createdDate}

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

        <div class="empty-state">

            <h2 class="h5">
                Опубликованных резюме нет
            </h2>

            <p class="text-secondary mb-0">
                Когда соискатели опубликуют
                резюме, они появятся здесь.
            </p>

        </div>

    </#if>

</@layout.layout>