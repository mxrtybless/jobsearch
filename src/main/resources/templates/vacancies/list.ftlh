<#import "../layout.ftlh" as layout>

<@layout.layout title="Вакансии | JobSearch">

    <div class="mb-4">

        <h1 class="h2 mb-2">
            Вакансии
        </h1>

        <p class="text-secondary mb-0">
            Актуальные вакансии работодателей
        </p>

    </div>

    <#if vacancies?? && vacancies?size gt 0>

        <div class="d-flex flex-column gap-3">

            <#list vacancies as vacancy>

                <div class="card border-0 shadow-sm list-card">

                    <div class="card-body p-4">

                        <div
                                class="d-flex
                                       flex-column
                                       flex-md-row
                                       justify-content-between
                                       gap-3">

                            <div class="flex-grow-1">

                                <h2 class="h4 mb-3">
                                    ${vacancy.name!}
                                </h2>

                                <div class="entity-meta mb-3">

                                    <div>
                                        <span class="text-secondary">
                                            Зарплата:
                                        </span>
                                        <strong>${vacancy.salary!}</strong>
                                    </div>

                                    <div>
                                        <span class="text-secondary">
                                            Опыт:
                                        </span>
                                        <strong>
                                            ${vacancy.expFrom!0}
                                            —
                                            ${vacancy.expTo!0}
                                            лет
                                        </strong>
                                    </div>

                                    <div>
                                        <span class="text-secondary">
                                            Категория:
                                        </span>
                                        <strong>${vacancy.categoryId!}</strong>
                                    </div>

                                </div>

                                <p class="vacancy-description mb-0">
                                    ${vacancy.description!}
                                </p>

                            </div>

                            <div
                                    class="vacancy-date
                                           text-md-end
                                           text-secondary
                                           small">

                                <div class="mb-1">
                                    Обновлено
                                </div>

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

        <div class="empty-state">

            <h2 class="h5">
                Активных вакансий пока нет
            </h2>

            <p class="text-secondary mb-0">
                Опубликованные вакансии появятся
                на этой странице.
            </p>

        </div>

    </#if>

</@layout.layout>
