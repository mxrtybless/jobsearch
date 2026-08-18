<#import "../layout.ftlh" as layout>

<@layout.layout title="Резюме | JobSearch">

    <div class="mb-4">
        <h1 class="h2 mb-2">
            Резюме соискателей
        </h1>

        <p class="text-secondary mb-0">
            Опубликованные резюме соискателей
        </p>
    </div>

    <#if resumes?? && resumes?size gt 0>

        <div class="d-flex flex-column gap-3">

            <#list resumes as resume>

                <div class="card border-0 shadow-sm list-card">
                    <div class="card-body p-4">

                        <div class="d-flex flex-column flex-md-row justify-content-between gap-3">

                            <div class="flex-grow-1">
                                <h2 class="h4 mb-3">
                                    ${resume.name!}
                                </h2>

                                <div class="entity-meta mb-3">
                                    <div>
                                        <span class="text-secondary">
                                            Желаемая зарплата:
                                        </span>
                                        <strong>
                                            ${resume.salary!}
                                        </strong>
                                    </div>

                                    <div>
                                        <span class="text-secondary">
                                            Категория:
                                        </span>
                                        <strong>
                                            ${resume.categoryId!}
                                        </strong>
                                    </div>

                                    <div>
                                        <span class="text-secondary">
                                            Соискатель ID:
                                        </span>
                                        <strong>
                                            ${resume.applicantId!}
                                        </strong>
                                    </div>
                                </div>

                                <#if resume.workExperienceInfo??
                                && resume.workExperienceInfo?size gt 0>
                                    <div class="small text-secondary">
                                        Опыт работы:
                                        <strong class="text-dark">
                                            ${resume.workExperienceInfo?size}
                                            записей
                                        </strong>
                                    </div>
                                </#if>
                            </div>

                            <div class="text-md-end text-secondary small">
                                <div class="mb-1">
                                    Обновлено
                                </div>

                                <strong class="text-dark">
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
                Активных резюме пока нет
            </h2>

            <p class="text-secondary mb-0">
                Опубликованные резюме появятся на этой странице.
            </p>
        </div>

    </#if>

</@layout.layout>
