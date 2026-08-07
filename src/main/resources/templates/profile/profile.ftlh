<#import "../layout.ftlh" as layout>

<@layout.layout title="Личный кабинет | JobSearch">

    <#if RequestParameters.updated??>

        <div class="alert alert-success">
            Данные профиля обновлены.
        </div>

    </#if>

    <#if RequestParameters.avatarUpdated??>

        <div class="alert alert-success">
            Фото профиля обновлено.
        </div>

    </#if>

    <#if RequestParameters.avatarError??>

        <div class="alert alert-danger">
            Не удалось обновить фото профиля.
        </div>

    </#if>

    <div class="card shadow-sm border-0 mb-4">

        <div class="card-body p-4">

            <div
                    class="d-flex flex-column
                           flex-md-row
                           align-items-md-center
                           gap-4">

                <div>

                    <#if user.avatar??
                    && user.avatar != "default-avatar.png">

                        <img
                                class="profile-avatar"
                                src="/images/download?filename=${user.avatar}"
                                alt="Фото профиля">

                    <#else>

                        <div class="profile-avatar-placeholder">
                            ${user.name?substring(0, 1)?upper_case}
                        </div>

                    </#if>

                </div>

                <div class="flex-grow-1">

                    <#if isApplicant>

                        <div class="text-secondary mb-1">
                            Личный кабинет соискателя
                        </div>

                        <h1 class="h3 mb-2">

                            ${user.name}

                            <#if user.surname??>
                                ${user.surname}
                            </#if>

                        </h1>

                    <#else>

                        <div class="text-secondary mb-1">
                            Личный кабинет работодателя
                        </div>

                        <h1 class="h3 mb-2">
                            ${user.name}
                        </h1>

                    </#if>

                    <p class="mb-1">
                        <strong>Email:</strong>
                        ${user.email}
                    </p>

                    <p class="mb-1">
                        <strong>Телефон:</strong>
                        ${user.phoneNumber}
                    </p>

                    <#if user.age??>

                        <p class="mb-0">
                            <strong>Возраст:</strong>
                            ${user.age}
                        </p>

                    </#if>

                </div>

                <div class="d-flex flex-column gap-2">

                    <button
                            class="btn btn-primary"
                            type="button"
                            data-bs-toggle="modal"
                            data-bs-target="#editProfileModal">
                        Редактировать
                    </button>

                    <a
                            class="btn btn-outline-primary"
                            href="/profile/edit">
                        Открыть форму отдельно
                    </a>

                </div>

            </div>

        </div>

    </div>

    <div class="row g-4">

        <div class="col-12 col-lg-6">

            <div class="card border-0 shadow-sm h-100">

                <div class="card-body p-4">

                    <h2 class="h5">
                        Фото профиля
                    </h2>

                    <form
                            action="/profile/avatar"
                            method="post"
                            enctype="multipart/form-data">

                        <div class="mb-3">

                            <input
                                    class="form-control"
                                    type="file"
                                    name="avatar"
                                    accept="image/*"
                                    required>

                        </div>

                        <button
                                class="btn btn-outline-primary"
                                type="submit">
                            Обновить фото
                        </button>

                    </form>

                </div>

            </div>

        </div>

        <div class="col-12 col-lg-6">

            <div class="card border-0 shadow-sm h-100">

                <div class="card-body p-4">

                    <#if isApplicant>

                        <h2 class="h5">
                            Мои резюме
                        </h2>

                        <p class="text-secondary">
                            Здесь будет список созданных
                            вами резюме.
                        </p>

                        <a
                                class="btn btn-outline-primary"
                                href="/resumes">
                            Перейти к резюме
                        </a>

                    <#else>

                        <h2 class="h5">
                            Мои вакансии
                        </h2>

                        <p class="text-secondary">
                            Здесь будет список созданных
                            вами вакансий.
                        </p>

                        <a
                                class="btn btn-outline-primary"
                                href="/vacancies">
                            Перейти к вакансиям
                        </a>

                    </#if>

                </div>

            </div>

        </div>

    </div>


    <div
            class="modal fade"
            id="editProfileModal"
            tabindex="-1"
            aria-hidden="true">

        <div class="modal-dialog modal-lg">

            <div class="modal-content">

                <div class="modal-header">

                    <h2 class="modal-title fs-5">
                        Редактирование профиля
                    </h2>

                    <button
                            type="button"
                            class="btn-close"
                            data-bs-dismiss="modal">
                    </button>

                </div>

                <form
                        action="/profile/edit"
                        method="post">

                    <div class="modal-body">

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="modalName">

                                <#if isApplicant>
                                    Имя
                                <#else>
                                    Имя компании
                                </#if>

                            </label>

                            <input
                                    id="modalName"
                                    class="form-control"
                                    type="text"
                                    name="name"
                                    value="${user.name}"
                                    required>

                        </div>

                        <#if isApplicant>

                            <div class="mb-3">

                                <label
                                        class="form-label"
                                        for="modalSurname">
                                    Фамилия
                                </label>

                                <input
                                        id="modalSurname"
                                        class="form-control"
                                        type="text"
                                        name="surname"
                                        value="${user.surname!}">

                            </div>

                        </#if>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="modalAge">
                                Возраст
                            </label>

                            <input
                                    id="modalAge"
                                    class="form-control"
                                    type="number"
                                    min="18"
                                    max="100"
                                    name="age"
                                    value="${user.age!}">

                        </div>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="modalEmail">
                                Email
                            </label>

                            <input
                                    id="modalEmail"
                                    class="form-control"
                                    type="email"
                                    name="email"
                                    value="${user.email}"
                                    required>

                        </div>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="modalPhone">
                                Телефон
                            </label>

                            <input
                                    id="modalPhone"
                                    class="form-control"
                                    type="text"
                                    name="phoneNumber"
                                    value="${user.phoneNumber}"
                                    required>

                        </div>

                    </div>

                    <div class="modal-footer">

                        <button
                                type="button"
                                class="btn btn-secondary"
                                data-bs-dismiss="modal">
                            Отмена
                        </button>

                        <button
                                type="submit"
                                class="btn btn-primary">
                            Сохранить
                        </button>

                    </div>

                </form>

            </div>

        </div>

    </div>

</@layout.layout>