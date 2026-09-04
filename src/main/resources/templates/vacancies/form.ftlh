<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#if formMode == "create">

    <#assign pageTitle =
            springMacroRequestContext.getMessage(
                    "vacancy.form.create.title"
            )>

    <#assign formAction = "/vacancies/form/create">

<#else>

    <#assign pageTitle =
            springMacroRequestContext.getMessage(
                    "vacancy.form.edit.title"
            )>

    <#assign formAction =
            "/vacancies/form/edit/" + vacancyId?c>

</#if>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-xl-8">

            <div class="card border-0 shadow-sm">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2">

                        <#if formMode == "create">

                            <@spring.message
                                    'vacancy.form.create.heading'/>

                        <#else>

                            <@spring.message
                                    'vacancy.form.edit.heading'/>

                        </#if>

                    </h1>


                    <p class="text-secondary mb-4">
                        <@spring.message 'vacancy.form.description'/>
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

                        <@layout.csrfInput/>


                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="name">

                                <@spring.message 'vacancy.form.name'/>

                            </label>

                            <@spring.formInput
                                    "vacancyDto.name"
                                    'id="name" class="form-control" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="categoryId">

                                <@spring.message 'vacancy.form.category'/>

                            </label>

                            <@spring.bind "vacancyDto.categoryId"/>

                            <select
                                    id="categoryId"
                                    class="form-select"
                                    name="categoryId"
                                    required>

                                <option value="">
                                    <@spring.message
                                            'vacancy.form.category.select'/>
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

                                <@spring.message 'vacancy.form.salary'/>

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

                                <@spring.message 'vacancy.form.details'/>

                            </label>

                            <@spring.formTextarea
                                    "vacancyDto.description"
                                    'id="description" class="form-control" rows="7" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="row g-3 mb-3">

                            <div class="col-12 col-md-6">

                                <label
                                        class="form-label"
                                        for="expFrom">

                                    <@spring.message
                                            'vacancy.form.experienceFrom'/>

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

                                    <@spring.message
                                            'vacancy.form.experienceTo'/>

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

                                <@spring.message 'vacancy.form.publish'/>

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

                                    <@spring.message
                                            'vacancy.form.create.submit'/>

                                <#else>

                                    <@spring.message
                                            'vacancy.form.edit.submit'/>

                                </#if>

                            </button>


                            <a
                                    class="btn btn-outline-secondary"
                                    href="/profile">

                                <@spring.message 'vacancy.form.cancel'/>

                            </a>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>