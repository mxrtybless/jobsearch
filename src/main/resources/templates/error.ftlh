<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign errorStatus = status!500>

<#assign errorTitleKey = "error.title.generic">
<#assign errorMessageKey = "error.message.generic">


<#if errorStatus == 400>

    <#assign errorTitleKey = "error.title.400">
    <#assign errorMessageKey = "error.message.400">

<#elseif errorStatus == 403>

    <#assign errorTitleKey = "error.title.403">
    <#assign errorMessageKey = "error.message.403">

<#elseif errorStatus == 404>

    <#assign errorTitleKey = "error.title.404">
    <#assign errorMessageKey = "error.message.404">

<#elseif errorStatus == 409>

    <#assign errorTitleKey = "error.title.409">
    <#assign errorMessageKey = "error.message.409">

<#elseif errorStatus == 500>

    <#assign errorTitleKey = "error.title.500">
    <#assign errorMessageKey = "error.message.500">

</#if>


<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "error.page.title"
        )>


<@layout.layout title=errorStatus?string + " | " + pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-lg-8">

            <div class="card border-0 shadow-sm">

                <div class="card-body p-4 p-md-5 text-center">

                    <div class="display-1 fw-bold text-primary mb-3">
                        ${errorStatus}
                    </div>

                    <h1 class="h3 mb-3">
                        <@spring.message errorTitleKey/>
                    </h1>

                    <p class="text-secondary mb-3">
                        <@spring.message errorMessageKey/>
                    </p>

                    <#if path?? && path?has_content>

                        <p class="small text-secondary mb-4">

                            <@spring.message 'error.path'/>:

                            <code>
                                ${path}
                            </code>

                        </p>

                    </#if>

                    <div
                            class="d-flex flex-wrap justify-content-center gap-2">

                        <a
                                class="btn btn-primary"
                                href="/vacancies">

                            <@spring.message 'error.vacancies'/>

                        </a>

                        <button
                                class="btn btn-outline-secondary"
                                type="button"
                                onclick="history.back()">

                            <@spring.message 'error.back'/>

                        </button>

                    </div>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>