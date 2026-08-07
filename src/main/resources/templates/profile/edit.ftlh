<#import "../layout.ftlh" as layout>

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

                            <input
                                    id="name"
                                    class="form-control"
                                    type="text"
                                    name="name"
                                    value="${profileUpdateDto.name!}"
                                    required>

                            <#if errors??
                            && errors["name"]??>

                                <div class="text-danger small mt-1">
                                    ${errors["name"]}
                                </div>

                            </#if>

                        </div>

                        <#if isApplicant>

                            <div class="mb-3">

                                <label
                                        class="form-label"
                                        for="surname">
                                    Фамилия
                                </label>

                                <input
                                        id="surname"
                                        class="form-control"
                                        type="text"
                                        name="surname"
                                        value="${profileUpdateDto.surname!}">

                                <#if errors??
                                && errors["surname"]??>

                                    <div class="text-danger small mt-1">
                                        ${errors["surname"]}
                                    </div>

                                </#if>

                            </div>

                        </#if>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="age">
                                Возраст
                            </label>

                            <input
                                    id="age"
                                    class="form-control"
                                    type="number"
                                    name="age"
                                    min="18"
                                    max="100"
                                    value="${profileUpdateDto.age!}">

                            <#if errors??
                            && errors["age"]??>

                                <div class="text-danger small mt-1">
                                    ${errors["age"]}
                                </div>

                            </#if>

                        </div>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="email">
                                Email
                            </label>

                            <input
                                    id="email"
                                    class="form-control"
                                    type="email"
                                    name="email"
                                    value="${profileUpdateDto.email!}"
                                    required>

                            <#if errors??
                            && errors["email"]??>

                                <div class="text-danger small mt-1">
                                    ${errors["email"]}
                                </div>

                            </#if>

                        </div>

                        <div class="mb-4">

                            <label
                                    class="form-label"
                                    for="phoneNumber">
                                Телефон
                            </label>

                            <input
                                    id="phoneNumber"
                                    class="form-control"
                                    type="text"
                                    name="phoneNumber"
                                    value="${profileUpdateDto.phoneNumber!}"
                                    required>

                            <#if errors??
                            && errors["phoneNumber"]??>

                                <div class="text-danger small mt-1">
                                    ${errors["phoneNumber"]}
                                </div>

                            </#if>

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