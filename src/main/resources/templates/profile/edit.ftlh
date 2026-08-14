<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.layout title="Редактирование профиля | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-lg-7">

            <div class="card border-0 shadow-sm">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-4">
                        Редактирование профиля
                    </h1>

                    <form
                            action="/profile/edit"
                            method="post">

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="name">

                                <#if isApplicant>
                                    Имя
                                <#else>
                                    Имя компании
                                </#if>

                            </label>

                            <@spring.formInput
                                    "profileUpdateDto.name"
                                    'id="name" class="form-control" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <#if isApplicant>

                            <div class="mb-3">

                                <label
                                        class="form-label"
                                        for="surname">
                                    Фамилия
                                </label>

                                <@spring.formInput
                                        "profileUpdateDto.surname"
                                        'id="surname" class="form-control"'/>

                                <@spring.showErrors
                                        "<br>"
                                        "text-danger small"/>

                            </div>

                        </#if>

                        <div class="mb-4">

                            <label
                                    class="form-label"
                                    for="age">
                                Возраст
                            </label>

                            <@spring.formInput
                                    "profileUpdateDto.age"
                                    'id="age" class="form-control" min="18" max="100"'
                                    "number"/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <div class="alert alert-secondary small">
                            Email и номер телефона в этой форме не изменяются.
                        </div>

                        <div class="d-flex gap-2">

                            <button
                                    class="btn btn-primary"
                                    type="submit">
                                Сохранить
                            </button>

                            <a
                                    class="btn btn-outline-secondary"
                                    href="/profile">
                                Отмена
                            </a>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>