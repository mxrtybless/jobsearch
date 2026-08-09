<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#if formMode == "create">
    <#assign pageTitle = "Создание вакансии | JobSearch">
    <#assign formAction = "/vacancies/form/create">
<#else>
    <#assign pageTitle = "Редактирование вакансии | JobSearch">
    <#assign formAction = "/vacancies/form/edit/" + vacancyId?c>
</#if>

<@layout.layout title=pageTitle>

    <div class="row justify-content-center">

        <div class="col-12 col-xl-8">

            <div class="card border-0 shadow-sm">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2">
                        <#if formMode == "create">
                            Создание вакансии
                        <#else>
                            Редактирование вакансии
                        </#if>
                    </h1>

                    <p class="text-secondary mb-4">
                        Заполните данные вакансии.
                    </p>

                    <@spring.bind "vacancyDto.*"/>

                    <#if spring.status.error>

                        <div class="alert alert-danger">

                            <#list spring.status.errorMessages as error>
                                <div>${error}</div>
                            </#list>

                        </div>

                    </#if>

                    <form
                            action="${formAction}"
                            method="post">

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="name">
                                Название вакансии
                            </label>

                            <@spring.formInput
                                    "vacancyDto.name"
                                    'id="name" class="form-control" placeholder="Junior Java Developer" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="categoryId">
                                Категория
                            </label>

                            <@spring.bind "vacancyDto.categoryId"/>

                            <select
                                    id="categoryId"
                                    class="form-select"
                                    name="categoryId"
                                    required>

                                <option value="">
                                    Выберите категорию
                                </option>

                                <#list categories as category>

                                    <option
                                            value="${category.id?c}"
                                            <#if vacancyDto.categoryId??
                                            && vacancyDto.categoryId == category.id>
                                                selected
                                            </#if>>
                                        ${category.name}
                                    </option>

                                </#list>

                            </select>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="salary">
                                Заработная плата
                            </label>

                            <@spring.formInput
                                    "vacancyDto.salary"
                                    'id="salary" class="form-control" min="1" step="0.01" required'
                                    "number"/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="description">
                                Описание и требования
                            </label>

                            <@spring.formTextarea
                                    "vacancyDto.description"
                                    'id="description" class="form-control" rows="7" placeholder="Опишите обязанности и требования к соискателю" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <div class="row g-3 mb-3">

                            <div class="col-12 col-md-6">

                                <label
                                        class="form-label"
                                        for="expFrom">
                                    Опыт от, лет
                                </label>

                                <@spring.formInput
                                        "vacancyDto.expFrom"
                                        'id="expFrom" class="form-control" min="0" max="70" required'
                                        "number"/>

                                <@spring.showErrors
                                        "<br>"
                                        "text-danger small"/>

                            </div>

                            <div class="col-12 col-md-6">

                                <label
                                        class="form-label"
                                        for="expTo">
                                    Опыт до, лет
                                </label>

                                <@spring.formInput
                                        "vacancyDto.expTo"
                                        'id="expTo" class="form-control" min="0" max="70" required'
                                        "number"/>

                                <@spring.showErrors
                                        "<br>"
                                        "text-danger small"/>

                            </div>

                        </div>

                        <div class="form-check form-switch mb-4">

                            <@spring.formCheckbox
                                    "vacancyDto.isActive"
                                    'id="isActive" class="form-check-input"'/>

                            <label
                                    class="form-check-label"
                                    for="isActive">
                                Опубликовать вакансию
                            </label>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <div class="d-flex gap-2">

                            <button
                                    class="btn btn-primary"
                                    type="submit">

                                <#if formMode == "create">
                                    Создать вакансию
                                <#else>
                                    Сохранить изменения
                                </#if>

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
