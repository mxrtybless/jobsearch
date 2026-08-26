<#import "../layout.ftlh" as layout>

<@layout.layout title="Новый пароль | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-5">

            <div class="card shadow-sm border-0">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-4 text-center">
                        Новый пароль
                    </h1>

                    <#if error??>

                        <div class="alert alert-danger">
                            ${error}
                        </div>

                        <p class="text-center mb-0">
                            <a href="/auth/forgot_password">
                                Запросить новую ссылку
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
                                    Новый пароль
                                </label>

                                <input
                                        id="password"
                                        class="form-control"
                                        type="password"
                                        name="password"
                                        placeholder="Введите новый пароль"
                                        required
                                        autofocus>

                            </div>

                            <button
                                    class="btn btn-primary w-100"
                                    type="submit">
                                Изменить пароль
                            </button>

                        </form>

                    </#if>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>
