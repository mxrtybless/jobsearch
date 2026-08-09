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

    <#if RequestParameters.resumeCreated??>

        <div class="alert alert-success">
            Резюме успешно создано.
        </div>

    </#if>

    <#if RequestParameters.resumeUpdated??>

        <div class="alert alert-success">
            Резюме успешно обновлено.
        </div>

    </#if>

    <#if RequestParameters.resumeRefreshed??>

        <div class="alert alert-success">
            Дата обновления резюме изменена.
        </div>

    </#if>

    <#if RequestParameters.vacancyCreated??>

        <div class="alert alert-success">
            Вакансия успешно создана.
        </div>

    </#if>

    <#if RequestParameters.vacancyUpdated??>

        <div class="alert alert-success">
            Вакансия успешно обновлена.
        </div>

    </#if>

    <#if RequestParameters.vacancyRefreshed??>

        <div class="alert alert-success">
            Дата обновления вакансии изменена.
        </div>

    </#if>


    <div class="card shadow-sm border-0 mb-4">

        <div class="card-body p-4">

            <div
                    class="d-flex
                           flex-column
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

                        <div
                                class="profile-avatar-placeholder">

                            ${user.name
                                    ?substring(0, 1)
                                    ?upper_case}

                        </div>

                    </#if>

                </div>


                <div class="flex-grow-1">

                    <#if isApplicant>

                        <div
                                class="text-secondary mb-1">
                            Личный кабинет соискателя
                        </div>

                        <h1 class="h3 mb-2">

                            ${user.name}

                            <#if user.surname??>
                                ${user.surname}
                            </#if>

                        </h1>

                    <#else>

                        <div
                                class="text-secondary mb-1">
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


                <div
                        class="d-flex
                               flex-column
                               gap-2">

                    <button
                            class="btn btn-primary"
                            type="button"
                            data-bs-toggle="modal"
                            data-bs-target="#editProfileModal">
                        Редактировать
                    </button>

                    <a
                            class="btn
                                   btn-outline-primary"
                            href="/profile/edit">
                        Открыть форму отдельно
                    </a>

                </div>

            </div>

        </div>

    </div>


    <#if isApplicant>

        <div
                class="d-flex
                       align-items-center
                       justify-content-between
                       mb-3">

            <div>

                <h2 class="h4 mb-1">
                    Мои резюме
                </h2>

                <p
                        class="text-secondary mb-0">
                    Резюме видны только владельцу
                    профиля.
                </p>

            </div>

            <a
                    class="btn btn-primary"
                    href="/resumes/form/create">
                Создать резюме
            </a>

        </div>


        <#if resumes?? && resumes?size gt 0>

            <div class="row g-3 mb-4">

                <#list resumes as resume>

                    <div
                            class="col-12
                                   col-md-6
                                   col-lg-4">

                        <div
                                class="card
                                       border-0
                                       shadow-sm
                                       h-100">

                            <div class="card-body">

                                <div
                                        class="d-flex
                                               justify-content-between
                                               gap-2
                                               mb-3">

                                    <h3 class="h5 mb-0">
                                        ${resume.name!}
                                    </h3>

                                    <#if resume.isActive??>

                                        <#if resume.isActive>

                                            <span
                                                    class="badge
                                                           text-bg-success
                                                           align-self-start">
                                                Опубликовано
                                            </span>

                                        <#else>

                                            <span
                                                    class="badge
                                                           text-bg-secondary
                                                           align-self-start">
                                                Не опубликовано
                                            </span>

                                        </#if>

                                    </#if>

                                </div>


                                <p class="mb-2">

                                    <strong>Зарплата:</strong>

                                    ${resume.salary!}

                                </p>


                                <p
                                        class="text-secondary
                                               small
                                               mb-0">

                                    <strong>
                                        Дата обновления:
                                    </strong>

                                    <#if resume.updateTime??>

                                        ${resume.updateTime}

                                    <#elseif resume.createdDate??>

                                        ${resume.createdDate}

                                    <#else>

                                        —

                                    </#if>

                                </p>

                                <div class="d-flex gap-2 mt-3">

                                    <form
                                            action="/resumes/form/update/${resume.id?c}"
                                            method="post">

                                        <button
                                                class="btn btn-outline-primary btn-sm"
                                                type="submit">
                                            Обновить
                                        </button>

                                    </form>

                                    <a
                                            class="btn btn-outline-secondary btn-sm"
                                            href="/resumes/form/edit/${resume.id?c}">
                                        Редактировать
                                    </a>

                                </div>

                            </div>

                        </div>

                    </div>

                </#list>

            </div>

        <#else>

            <div
                    class="empty-state mb-4">

                <h3 class="h5">
                    Резюме пока нет
                </h3>

                <p class="mb-0 text-secondary">
                    Здесь появятся созданные вами
                    резюме.
                </p>

            </div>

        </#if>

    </#if>


    <#if isEmployer>

        <div
                class="d-flex
                       align-items-center
                       justify-content-between
                       mb-3">

            <div>

                <h2 class="h4 mb-1">
                    Мои вакансии
                </h2>

                <p
                        class="text-secondary mb-0">
                    Здесь отображаются только
                    ваши вакансии.
                </p>

            </div>

            <a
                    class="btn btn-primary"
                    href="/vacancies/form/create">
                Создать вакансию
            </a>

        </div>


        <#if vacancies??
        && vacancies?size gt 0>

            <div class="row g-3 mb-4">

                <#list vacancies as vacancy>

                    <div
                            class="col-12
                                   col-md-6
                                   col-lg-4">

                        <div
                                class="card
                                       border-0
                                       shadow-sm
                                       h-100">

                            <div class="card-body">

                                <div
                                        class="d-flex
                                               justify-content-between
                                               gap-2
                                               mb-3">

                                    <h3 class="h5 mb-0">
                                        ${vacancy.name!}
                                    </h3>

                                    <#if vacancy.isActive??>

                                        <#if vacancy.isActive>

                                            <span
                                                    class="badge
                                                           text-bg-success
                                                           align-self-start">
                                                Опубликовано
                                            </span>

                                        <#else>

                                            <span
                                                    class="badge
                                                           text-bg-secondary
                                                           align-self-start">
                                                Не опубликовано
                                            </span>

                                        </#if>

                                    </#if>

                                </div>


                                <p class="mb-2">

                                    <strong>Зарплата:</strong>

                                    ${vacancy.salary!}

                                </p>


                                <p
                                        class="text-secondary
                                               small
                                               mb-0">

                                    <strong>
                                        Дата обновления:
                                    </strong>

                                    <#if vacancy.updateTime??>

                                        ${vacancy.updateTime}

                                    <#elseif vacancy.createdDate??>

                                        ${vacancy.createdDate}

                                    <#else>

                                        —

                                    </#if>

                                </p>

                                <div class="d-flex gap-2 mt-3">

                                    <form
                                            action="/vacancies/form/update/${vacancy.id?c}"
                                            method="post">

                                        <button
                                                class="btn btn-outline-primary btn-sm"
                                                type="submit">
                                            Обновить
                                        </button>

                                    </form>

                                    <a
                                            class="btn btn-outline-secondary btn-sm"
                                            href="/vacancies/form/edit/${vacancy.id?c}">
                                        Редактировать
                                    </a>

                                </div>

                            </div>

                        </div>

                    </div>

                </#list>

            </div>

        <#else>

            <div
                    class="empty-state mb-4">

                <h3 class="h5">
                    Вакансий пока нет
                </h3>

                <p class="mb-0 text-secondary">
                    Здесь появятся созданные
                    вами вакансии.
                </p>

            </div>

        </#if>

    </#if>

    <div class="card border-0 shadow-sm">

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
                        class="btn
                               btn-outline-primary"
                        type="submit">
                    Обновить фото
                </button>

            </form>

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