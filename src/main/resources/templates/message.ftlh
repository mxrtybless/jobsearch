<#import "layout.ftlh" as layout>

<@layout.layout title="Восстановление пароля | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-md-8 col-lg-6">

            <div class="alert alert-info text-center">

                <h1 class="h4 mb-3">
                    Восстановление пароля
                </h1>

                <p class="mb-3">
                    ${message}
                </p>

                <a
                        class="btn btn-primary"
                        href="/auth/login">
                    Перейти ко входу
                </a>

            </div>

        </div>

    </div>

</@layout.layout>
