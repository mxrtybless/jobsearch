<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle>
    <@spring.message 'auth.reset.title'/>
</#assign>

<#assign passwordPlaceholder>
    <@spring.message 'auth.reset.password.placeholder'/>
</#assign>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-5">

            <div class="card shadow-sm border-0">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-4 text-center">
                        <@spring.message 'auth.reset.heading'/>
                    </h1>


                    <#if errorKey??>

                        <div class="alert alert-danger">
                            <@spring.message errorKey/>
                        </div>

                        <p class="text-center mb-0">

                            <a href="/auth/forgot_password">
                                <@spring.message 'auth.reset.newLink'/>
                            </a>

                        </p>


                    <#else>

                        <form
                                action="/auth/reset_password"
                                method="post">

                            <@layout.csrfInput/>

                            <input
                                    type="hidden"
                                    name="token"
                                    value="${token!}">


                            <div class="mb-4">

                                <label
                                        for="password"
                                        class="form-label">

                                    <@spring.message
                                            'auth.reset.password'/>

                                </label>

                                <input
                                        id="password"
                                        class="form-control"
                                        type="password"
                                        name="password"
                                        placeholder="${passwordPlaceholder?trim}"
                                        required
                                        autofocus>

                            </div>


                            <button
                                    class="btn btn-primary w-100"
                                    type="submit">

                                <@spring.message 'auth.reset.submit'/>

                            </button>

                        </form>

                    </#if>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>