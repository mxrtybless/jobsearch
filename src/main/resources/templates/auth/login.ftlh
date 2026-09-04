<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle>
    <@spring.message 'auth.login.title'/>
</#assign>

<#assign passwordPlaceholder>
    <@spring.message 'auth.login.password.placeholder'/>
</#assign>

<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-5">

            <div class="card shadow-sm border-0 auth-card">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2 text-center">
                        <@spring.message 'auth.login.heading'/>
                    </h1>

                    <p class="text-secondary text-center mb-4">
                        <@spring.message 'auth.login.description'/>
                    </p>


                    <#if RequestParameters.registered??>

                        <div class="alert alert-success">
                            <@spring.message 'auth.login.registered'/>
                        </div>

                    </#if>


                    <#if RequestParameters.error??>

                        <div class="alert alert-danger">
                            <@spring.message 'auth.login.error'/>
                        </div>

                    </#if>


                    <#if RequestParameters.logout??>

                        <div class="alert alert-success">
                            <@spring.message 'auth.login.logout'/>
                        </div>

                    </#if>


                    <form
                            action="/auth/login"
                            method="post">

                        <@layout.csrfInput/>


                        <div class="mb-3">

                            <label
                                    for="email"
                                    class="form-label">

                                <@spring.message 'auth.login.email'/>

                            </label>

                            <input
                                    id="email"
                                    class="form-control"
                                    type="email"
                                    name="username"
                                    placeholder="name@attractor.school"
                                    required>

                        </div>


                        <div class="mb-3">

                            <label
                                    for="password"
                                    class="form-label">

                                <@spring.message 'auth.login.password'/>

                            </label>

                            <input
                                    id="password"
                                    class="form-control"
                                    type="password"
                                    name="password"
                                    placeholder="${passwordPlaceholder?trim}"
                                    required>

                        </div>


                        <div class="mb-4 text-end">

                            <a href="/auth/forgot_password">
                                <@spring.message 'auth.login.forgot'/>
                            </a>

                        </div>


                        <button
                                class="btn btn-primary w-100"
                                type="submit">

                            <@spring.message 'auth.login.submit'/>

                        </button>

                    </form>


                    <p class="text-center text-secondary mt-4 mb-0">

                        <@spring.message 'auth.login.noAccount'/>

                        <a href="/auth/register">
                            <@spring.message 'auth.login.register'/>
                        </a>

                    </p>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>