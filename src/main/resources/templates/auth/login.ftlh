<#import "../layout.ftlh" as layout>

<@layout.layout title="Вход | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-5">

            <div
                    class="card shadow-sm border-0 auth-card">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2 text-center">
                        Вход в JobSearch
                    </h1>

                    <p
                            class="text-secondary text-center mb-4">
                        Введите email и пароль
                    </p>

                    <#if RequestParameters.registered??>

                        <div class="alert alert-success">
                            Регистрация прошла успешно.
                            Теперь войдите в аккаунт.
                        </div>

                    </#if>

                    <#if RequestParameters.error??>

                        <div class="alert alert-danger">
                            Неверный email или пароль
                        </div>

                    </#if>

                    <#if RequestParameters.logout??>

                        <div class="alert alert-success">
                            Вы вышли из аккаунта
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
                                Email
                            </label>

                            <input
                                    id="email"
                                    class="form-control"
                                    type="email"
                                    name="username"
                                    placeholder="name@example.com"
                                    required>

                        </div>

                        <div class="mb-4">

                            <label
                                    for="password"
                                    class="form-label">
                                Пароль
                            </label>

                            <input
                                    id="password"
                                    class="form-control"
                                    type="password"
                                    name="password"
                                    placeholder="Введите пароль"
                                    required>

                        </div>

                        <button
                                class="btn btn-primary w-100"
                                type="submit">
                            Войти
                        </button>

                    </form>

                    <p
                            class="text-center text-secondary mt-4 mb-0">

                        Нет аккаунта?

                        <a href="/auth/register">
                            Зарегистрироваться
                        </a>

                    </p>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>