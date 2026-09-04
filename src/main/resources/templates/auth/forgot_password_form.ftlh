<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "auth.forgot.title"
        )>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-5">

            <div class="card shadow-sm border-0">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-3 text-center">
                        <@spring.message 'auth.forgot.heading'/>
                    </h1>

                    <p class="text-secondary text-center mb-4">
                        <@spring.message 'auth.forgot.description'/>
                    </p>


                    <#if errorKey??>

                        <div class="alert alert-danger">
                            <@spring.message errorKey/>
                        </div>

                    </#if>


                    <#if messageKey??>

                        <div class="alert alert-success">
                            <@spring.message messageKey/>
                        </div>

                    </#if>


                    <form
                            action="/auth/forgot_password"
                            method="post">

                        <@layout.csrfInput/>


                        <div class="mb-4">

                            <label
                                    for="email"
                                    class="form-label">

                                <@spring.message 'auth.forgot.email'/>

                            </label>

                            <input
                                    id="email"
                                    class="form-control"
                                    type="email"
                                    name="email"
                                    placeholder="name@attractor.school"
                                    required
                                    autofocus>

                        </div>


                        <button
                                class="btn btn-primary w-100"
                                type="submit">

                            <@spring.message 'auth.forgot.submit'/>

                        </button>

                    </form>


                    <p class="text-center mt-4 mb-0">

                        <a href="/auth/login">
                            <@spring.message 'auth.forgot.back'/>
                        </a>

                    </p>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>