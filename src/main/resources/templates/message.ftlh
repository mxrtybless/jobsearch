<#import "layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "auth.message.title"
        )>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-6">

            <div class="alert alert-info text-center">

                <h1 class="h4 mb-3">
                    <@spring.message 'auth.message.heading'/>
                </h1>


                <#if messageKey??>

                    <p class="mb-3">
                        <@spring.message messageKey/>
                    </p>

                </#if>


                <a
                        class="btn btn-primary"
                        href="/auth/login">

                    <@spring.message 'auth.message.login'/>

                </a>

            </div>

        </div>

    </div>

</@layout.layout>