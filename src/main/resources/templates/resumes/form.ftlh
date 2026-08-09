<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.layout title=(formMode == "create")?then("Создание резюме | JobSearch", "Редактирование резюме | JobSearch")>

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
                        Заполните основную информацию,
                        контакты, опыт работы и образование.
                    </p>

                    <@spring.bind "resumeDto"/>

                    <#if spring.status.error>

                        <div class="alert alert-danger">

                            <#list spring.status.errorMessages as error>
                                <div>${error}</div>
                            </#list>

                        </div>

                    </#if>

                    <form
                            action=<#if formMode == "create">"/resumes/form/create"<#else>"/resumes/form/edit/${resumeId?c}"</#if>
                            method="post">

                        <h2 class="h5 mb-3">
                            Основная информация
                        </h2>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="name">
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

                            <label
                                    class="form-label"
                                    for="categoryId">
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

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="salary">
                                Ожидаемая зарплата
                            </label>

                            <@spring.formInput
                                    "resumeDto.salary"
                                    'id="salary" class="form-control" min="1" step="0.01" required'
                                    'number'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <div class="form-check form-switch mb-4">

                            <@spring.formCheckbox
                                    "resumeDto.isActive"
                                    'id="isActive" class="form-check-input"'/>

                            <label
                                    class="form-check-label"
                                    for="isActive">
                                Опубликовать резюме
                            </label>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>

                        <hr class="my-4">

                        <h2 class="h5 mb-3">
                            Контактные данные
                        </h2>

                        <p class="text-secondary small">
                            Необязательные поля можно оставить пустыми.
                        </p>

                        <div class="row g-3 mb-4">

                            <#list contactTypes as contactType>

                                <#assign contactIndex = contactType_index>
                                <#assign contactPath =
                                        "resumeDto.contactInfo["
                                        + contactIndex
                                        + "].value">

                                <div class="col-12 col-md-6">

                                    <label class="form-label">

                                        <#if contactType.type == "EMAIL">
                                            Email
                                        <#elseif contactType.type == "PHONE">
                                            Телефон
                                        <#elseif contactType.type == "TELEGRAM">
                                            Telegram
                                        <#elseif contactType.type == "FACEBOOK">
                                            Facebook
                                        <#elseif contactType.type == "LINKEDIN">
                                            LinkedIn
                                        <#else>
                                            ${contactType.type}
                                        </#if>

                                    </label>

                                    <input
                                            type="hidden"
                                            name="contactInfo[${contactIndex}].typeId"
                                            value="${contactType.id?c}">

                                    <@spring.formInput
                                            contactPath
                                            'class="form-control"'/>

                                    <@spring.showErrors
                                            "<br>"
                                            "text-danger small"/>

                                </div>

                            </#list>

                        </div>

                        <hr class="my-4">

                        <div
                                class="d-flex
                                       align-items-center
                                       justify-content-between
                                       mb-3">

                            <div>

                                <h2 class="h5 mb-1">
                                    Опыт работы
                                </h2>

                                <div class="text-secondary small">
                                    Можно добавить несколько мест работы.
                                </div>

                            </div>

                            <button
                                    type="button"
                                    class="btn btn-outline-primary btn-sm"
                                    onclick="addWorkExperience()">
                                Добавить опыт
                            </button>

                        </div>

                        <div
                                id="workExperienceContainer"
                                class="d-flex flex-column gap-3 mb-4">

                            <#if resumeDto.workExperienceInfo??>

                                <#list resumeDto.workExperienceInfo as work>

                                    <#assign workIndex = work_index>

                                    <div
                                            class="card
                                                   bg-light
                                                   border-0
                                                   p-3
                                                   repeatable-section"
                                            data-section="workExperienceInfo">

                                        <div
                                                class="d-flex
                                                       justify-content-between
                                                       align-items-center
                                                       mb-3">

                                            <strong>
                                                Опыт работы
                                            </strong>

                                            <button
                                                    type="button"
                                                    class="btn btn-outline-danger btn-sm"
                                                    onclick="removeSection(this, 'workExperienceInfo')">
                                                Удалить
                                            </button>

                                        </div>

                                        <div class="row g-3">

                                            <div class="col-12 col-md-4">

                                                <label class="form-label">
                                                    Лет работы
                                                </label>

                                                <#assign path =
                                                        "resumeDto.workExperienceInfo["
                                                        + workIndex
                                                        + "].years">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control" min="0" max="70" required'
                                                        'number'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                            <div class="col-12 col-md-8">

                                                <label class="form-label">
                                                    Компания
                                                </label>

                                                <#assign path =
                                                        "resumeDto.workExperienceInfo["
                                                        + workIndex
                                                        + "].companyName">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control" required'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                            <div class="col-12">

                                                <label class="form-label">
                                                    Должность
                                                </label>

                                                <#assign path =
                                                        "resumeDto.workExperienceInfo["
                                                        + workIndex
                                                        + "].position">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control" required'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                            <div class="col-12">

                                                <label class="form-label">
                                                    Обязанности
                                                </label>

                                                <#assign path =
                                                        "resumeDto.workExperienceInfo["
                                                        + workIndex
                                                        + "].responsibilities">

                                                <@spring.formTextarea
                                                        path
                                                        'class="form-control" rows="3" required'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                        </div>

                                    </div>

                                </#list>

                            </#if>

                        </div>

                        <hr class="my-4">

                        <div
                                class="d-flex
                                       align-items-center
                                       justify-content-between
                                       mb-3">

                            <div>

                                <h2 class="h5 mb-1">
                                    Образование
                                </h2>

                                <div class="text-secondary small">
                                    Можно добавить образование
                                    или пройденные курсы.
                                </div>

                            </div>

                            <button
                                    type="button"
                                    class="btn btn-outline-primary btn-sm"
                                    onclick="addEducation()">
                                Добавить образование
                            </button>

                        </div>

                        <div
                                id="educationContainer"
                                class="d-flex flex-column gap-3 mb-4">

                            <#if resumeDto.educationInfo??>

                                <#list resumeDto.educationInfo as education>

                                    <#assign educationIndex =
                                            education_index>

                                    <div
                                            class="card
                                                   bg-light
                                                   border-0
                                                   p-3
                                                   repeatable-section"
                                            data-section="educationInfo">

                                        <div
                                                class="d-flex
                                                       justify-content-between
                                                       align-items-center
                                                       mb-3">

                                            <strong>
                                                Образование
                                            </strong>

                                            <button
                                                    type="button"
                                                    class="btn btn-outline-danger btn-sm"
                                                    onclick="removeSection(this, 'educationInfo')">
                                                Удалить
                                            </button>

                                        </div>

                                        <div class="row g-3">

                                            <div class="col-12 col-md-6">

                                                <label class="form-label">
                                                    Учебное заведение
                                                </label>

                                                <#assign path =
                                                        "resumeDto.educationInfo["
                                                        + educationIndex
                                                        + "].institution">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control" required'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                            <div class="col-12 col-md-6">

                                                <label class="form-label">
                                                    Программа
                                                </label>

                                                <#assign path =
                                                        "resumeDto.educationInfo["
                                                        + educationIndex
                                                        + "].program">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control" required'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                            <div class="col-12 col-md-4">

                                                <label class="form-label">
                                                    Дата начала
                                                </label>

                                                <#assign path =
                                                        "resumeDto.educationInfo["
                                                        + educationIndex
                                                        + "].startDate">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control" required'
                                                        'date'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                            <div class="col-12 col-md-4">

                                                <label class="form-label">
                                                    Дата окончания
                                                </label>

                                                <#assign path =
                                                        "resumeDto.educationInfo["
                                                        + educationIndex
                                                        + "].endDate">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control"'
                                                        'date'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                            <div class="col-12 col-md-4">

                                                <label class="form-label">
                                                    Степень / сертификат
                                                </label>

                                                <#assign path =
                                                        "resumeDto.educationInfo["
                                                        + educationIndex
                                                        + "].degree">

                                                <@spring.formInput
                                                        path
                                                        'class="form-control" required'/>

                                                <@spring.showErrors
                                                        "<br>"
                                                        "text-danger small"/>

                                            </div>

                                        </div>

                                    </div>

                                </#list>

                            </#if>

                        </div>

                        <div class="d-flex gap-2 mt-4">

                            <button
                                    class="btn btn-primary"
                                    type="submit">

                                <#if formMode == "create">
                                    Создать резюме
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

    <script>
        function addWorkExperience() {
            const container =
                document.getElementById(
                    "workExperienceContainer"
                );

            const index =
                container.querySelectorAll(
                    ".repeatable-section"
                ).length;

            const section =
                document.createElement("div");

            section.className =
                "card bg-light border-0 p-3 repeatable-section";

            section.dataset.section =
                "workExperienceInfo";

            section.innerHTML =
                '<div class="d-flex justify-content-between align-items-center mb-3">' +
                    '<strong>Опыт работы</strong>' +
                    '<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeSection(this, \'workExperienceInfo\')">Удалить</button>' +
                '</div>' +
                '<div class="row g-3">' +
                    '<div class="col-12 col-md-4">' +
                        '<label class="form-label">Лет работы</label>' +
                        '<input class="form-control" type="number" min="0" max="70" required name="workExperienceInfo[' + index + '].years">' +
                    '</div>' +
                    '<div class="col-12 col-md-8">' +
                        '<label class="form-label">Компания</label>' +
                        '<input class="form-control" type="text" required name="workExperienceInfo[' + index + '].companyName">' +
                    '</div>' +
                    '<div class="col-12">' +
                        '<label class="form-label">Должность</label>' +
                        '<input class="form-control" type="text" required name="workExperienceInfo[' + index + '].position">' +
                    '</div>' +
                    '<div class="col-12">' +
                        '<label class="form-label">Обязанности</label>' +
                        '<textarea class="form-control" rows="3" required name="workExperienceInfo[' + index + '].responsibilities"></textarea>' +
                    '</div>' +
                '</div>';

            container.appendChild(section);
        }

        function addEducation() {
            const container =
                document.getElementById(
                    "educationContainer"
                );

            const index =
                container.querySelectorAll(
                    ".repeatable-section"
                ).length;

            const section =
                document.createElement("div");

            section.className =
                "card bg-light border-0 p-3 repeatable-section";

            section.dataset.section =
                "educationInfo";

            section.innerHTML =
                '<div class="d-flex justify-content-between align-items-center mb-3">' +
                    '<strong>Образование</strong>' +
                    '<button type="button" class="btn btn-outline-danger btn-sm" onclick="removeSection(this, \'educationInfo\')">Удалить</button>' +
                '</div>' +
                '<div class="row g-3">' +
                    '<div class="col-12 col-md-6">' +
                        '<label class="form-label">Учебное заведение</label>' +
                        '<input class="form-control" type="text" required name="educationInfo[' + index + '].institution">' +
                    '</div>' +
                    '<div class="col-12 col-md-6">' +
                        '<label class="form-label">Программа</label>' +
                        '<input class="form-control" type="text" required name="educationInfo[' + index + '].program">' +
                    '</div>' +
                    '<div class="col-12 col-md-4">' +
                        '<label class="form-label">Дата начала</label>' +
                        '<input class="form-control" type="date" required name="educationInfo[' + index + '].startDate">' +
                    '</div>' +
                    '<div class="col-12 col-md-4">' +
                        '<label class="form-label">Дата окончания</label>' +
                        '<input class="form-control" type="date" name="educationInfo[' + index + '].endDate">' +
                    '</div>' +
                    '<div class="col-12 col-md-4">' +
                        '<label class="form-label">Степень / сертификат</label>' +
                        '<input class="form-control" type="text" required name="educationInfo[' + index + '].degree">' +
                    '</div>' +
                '</div>';

            container.appendChild(section);
        }

        function removeSection(
            button,
            collectionName
        ) {
            button.closest(
                ".repeatable-section"
            ).remove();

            reindexSections(
                collectionName
            );
        }

        function reindexSections(
            collectionName
        ) {
            const containerId =
                collectionName === "educationInfo"
                    ? "educationContainer"
                    : "workExperienceContainer";

            const sections =
                document
                    .getElementById(containerId)
                    .querySelectorAll(
                        ".repeatable-section"
                    );

            sections.forEach(
                function (section, index) {

                    section
                        .querySelectorAll(
                            "input, textarea, select"
                        )
                        .forEach(function (field) {

                            if (field.name) {
                                field.name =
                                    field.name.replace(
                                        new RegExp(
                                            collectionName
                                            + "\\[\\d+\\]",
                                            "g"
                                        ),
                                        collectionName
                                        + "["
                                        + index
                                        + "]"
                                    );
                            }
                        });
                }
            );
        }
    </script>

</@layout.layout>