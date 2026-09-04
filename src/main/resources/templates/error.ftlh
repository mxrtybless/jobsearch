<#import "layout.ftlh" as layout>

<#assign errorStatus = status!500>

<#if reason?? && reason?has_content>
    <#assign errorReason = reason>
<#elseif error?? && error?has_content>
    <#assign errorReason = error>
<#else>
    <#assign errorReason = "Ошибка">
</#if>

<@layout.layout title=(errorStatus?string + " | JobSearch")>

    <div class="row justify-content-center">

        <div class="col-12 col-lg-8">

            <div class="card border-0 shadow-sm">

                <div class="card-body p-4 p-md-5 text-center">

                    <div class="display-1 fw-bold text-primary mb-3">
                        ${errorStatus}
                    </div>

                    <h1 class="h3 mb-3">
                        ${errorReason}
                    </h1>

                    <#if message??
                    && message?has_content
                    && message != "No message available">

                        <p class="text-secondary mb-3">
                            ${message}
                        </p>

                    </#if>

                    <#if path?? && path?has_content>

                        <p class="small text-secondary mb-4">
                            Путь:
                            <code>${path}</code>
                        </p>

                    </#if>

                    <div class="d-flex flex-wrap justify-content-center gap-2">

                        <a
                                class="btn btn-primary"
                                href="/vacancies">
                            К вакансиям
                        </a>

                        <button
                                class="btn btn-outline-secondary"
                                type="button"
                                onclick="history.back()">
                            Назад
                        </button>

                    </div>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>