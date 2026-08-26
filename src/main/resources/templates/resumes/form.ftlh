<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#if formMode == "create">
    <#assign pageTitle = "Создание резюме | JobSearch">
    <#assign formAction = "/resumes/form/create">
<#else>
    <#assign pageTitle = "Редактирование резюме | JobSearch">
    <#assign formAction = "/resumes/form/edit/" + resumeId?c>
</#if>

<@layout.layout title=pageTitle>

    <div class="row justify-content-center">
        <div class="col-12 col-xl-9">
            <div class="card border-0 shadow-sm">
                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2">
                        <#if formMode == "create">
                            Создание резюме
                        <#else>
                            Редактирование резюме
                        </#if>
                    </h1>

                    <p class="text-secondary mb-4">
                        Заполните данные резюме.
                    </p>

                    <@spring.bind "resumeDto.*"/>

                    <#if spring.status.error>
                        <div class="alert alert-danger">
                            <#list spring.status.errorMessages as error>
                                <div>${error}</div>
                            </#list>
                        </div>
                    </#if>

                    <form action="${formAction}" method="post">

                        <@layout.csrfInput/>

                        <div class="mb-3">
                            <label class="form-label" for="name">
                                Название резюме
                            </label>

                            <@spring.formInput
                                    "resumeDto.name"
                                    'id="name" class="form-control" placeholder="Java Developer" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="categoryId">
                                Категория
                            </label>

                            <@spring.bind "resumeDto.categoryId"/>

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
                                            <#if resumeDto.categoryId??
                                            && resumeDto.categoryId == category.id>
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

                        <div class="mb-4">
                            <label class="form-label" for="salary">
                                Желаемая зарплата
                            </label>

                            <@spring.formInput
                                    "resumeDto.salary"
                                    'id="salary" class="form-control" min="1" step="0.01" required'
                                    "number"/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>
                        </div>

                        <hr class="my-4">

                        <h2 class="h5 mb-3">
                            Контактная информация
                        </h2>

                        <div class="row g-3 mb-4">
                            <#list contactTypes as contactType>
                                <#assign contactIndex = contactType?index>

                                <div class="col-12 col-md-6">
                                    <label class="form-label">
                                        ${contactType.type}
                                    </label>

                                    <input
                                            type="hidden"
                                            name="contactInfo[${contactIndex}].typeId"
                                            value="${contactType.id?c}">

                                    <@spring.formInput
                                            "resumeDto.contactInfo[${contactIndex}].value"
                                            'class="form-control" placeholder="Введите значение"'/>

                                    <@spring.showErrors
                                            "<br>"
                                            "text-danger small"/>
                                </div>
                            </#list>
                        </div>

                        <hr class="my-4">

                        <h2 class="h5 mb-3">
                            Опыт работы
                        </h2>

                        <div class="card border mb-4">
                            <div class="card-body">
                                <div class="row g-3">
                                    <div class="col-12 col-md-6">
                                        <label class="form-label">Компания</label>
                                        <@spring.formInput
                                                "resumeDto.workExperienceInfo[0].companyName"
                                                'class="form-control" required'/>
                                    </div>

                                    <div class="col-12 col-md-6">
                                        <label class="form-label">Должность</label>
                                        <@spring.formInput
                                                "resumeDto.workExperienceInfo[0].position"
                                                'class="form-control" required'/>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <label class="form-label">Лет опыта</label>
                                        <@spring.formInput
                                                "resumeDto.workExperienceInfo[0].years"
                                                'class="form-control" min="0" max="70" required'
                                                "number"/>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label">Обязанности</label>
                                        <@spring.formTextarea
                                                "resumeDto.workExperienceInfo[0].responsibilities"
                                                'class="form-control" rows="3" required'/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr class="my-4">

                        <h2 class="h5 mb-3">
                            Образование и курсы
                        </h2>

                        <div class="card border mb-4">
                            <div class="card-body">
                                <div class="row g-3">
                                    <div class="col-12 col-md-6">
                                        <label class="form-label">Учебное заведение</label>
                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].institution"
                                                'class="form-control" required'/>
                                    </div>

                                    <div class="col-12 col-md-6">
                                        <label class="form-label">Программа</label>
                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].program"
                                                'class="form-control" required'/>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <label class="form-label">Дата начала</label>
                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].startDate"
                                                'class="form-control" required'
                                                "date"/>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <label class="form-label">Дата окончания</label>
                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].endDate"
                                                'class="form-control"'
                                                "date"/>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <label class="form-label">Степень / сертификат</label>
                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].degree"
                                                'class="form-control" required'/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="form-check form-switch mb-4">
                            <@spring.formCheckbox
                                    "resumeDto.isActive"
                                    'id="isActive" class="form-check-input"'/>

                            <label class="form-check-label" for="isActive">
                                Опубликовать резюме
                            </label>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>
                        </div>

                        <div class="d-flex gap-2">
                            <button class="btn btn-primary" type="submit">
                                <#if formMode == "create">
                                    Создать резюме
                                <#else>
                                    Сохранить изменения
                                </#if>
                            </button>

                            <a class="btn btn-outline-secondary" href="/profile">
                                Отмена
                            </a>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>



</@layout.layout>
