<#import "../layout.ftlh" as layout>

<@layout.layout title="Восстановление пароля | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-5">

            <div class="card shadow-sm border-0">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-3 text-center">
                        Восстановление пароля
                    </h1>

                    <p class="text-secondary text-center mb-4">
                        Введите email, и мы отправим ссылку для смены пароля.
                    </p>

                    <#if error??>
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                    </#if>

                    <#if message??>
                        <div class="alert alert-success">
                            ${message}
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
                                Email
                            </label>

                            <input
                                    id="email"
                                    class="form-control"
                                    type="email"
                                    name="email"
                                    placeholder="name@example.com"
                                    required
                                    autofocus>

                        </div>

                        <button
                                class="btn btn-primary w-100"
                                type="submit">
                            Отправить ссылку
                        </button>

                    </form>

                    <p class="text-center mt-4 mb-0">
                        <a href="/auth/login">
                            Вернуться ко входу
                        </a>
                    </p>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>
