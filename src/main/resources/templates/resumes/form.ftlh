<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign english = .locale?starts_with("en")>
<#assign removeLabel = english?then("Remove", "Удалить")>
<#assign addWorkLabel = english?then("Add work experience", "Добавить опыт работы")>
<#assign addEducationLabel = english?then("Add education", "Добавить образование")>

<#if formMode == "create">
    <#assign pageTitle =
        springMacroRequestContext.getMessage("resume.form.create.title")>
    <#assign formAction = "/resumes/form/create">
<#else>
    <#assign pageTitle =
        springMacroRequestContext.getMessage("resume.form.edit.title")>
    <#assign formAction = "/resumes/form/edit/" + resumeId?c>
</#if>

<@layout.layout title=pageTitle>
    <div class="row justify-content-center">
        <div class="col-12 col-xl-9">
            <div class="card border-0 shadow-sm">
                <div class="card-body p-4 p-md-5">
                    <h1 class="h3 mb-2">${pageTitle}</h1>

                    <p class="text-secondary mb-4">
                        <@spring.message 'resume.form.description'/>
                    </p>

                    <@spring.bind "resumeDto.*"/>
                    <#if spring.status.error>
                        <div class="alert alert-danger" role="alert">
                            <#list spring.status.errorMessages as error>
                                <div>${error}</div>
                            </#list>
                        </div>
                    </#if>

                    <form id="resume-form"
                          action="${formAction}"
                          method="post">

                        <@layout.csrfInput/>

                        <div class="mb-3">
                            <label class="form-label" for="name">
                                <@spring.message 'resume.form.name'/>
                            </label>

                            <@spring.formInput "resumeDto.name"
                                'id="name" class="form-control" maxlength="150" required'/>

                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="categoryId">
                                <@spring.message 'resume.form.category'/>
                            </label>

                            <@spring.bind "resumeDto.categoryId"/>

                            <select id="categoryId"
                                    class="form-select"
                                    name="categoryId"
                                    required>
                                <option value="">
                                    <@spring.message 'resume.form.category.select'/>
                                </option>

                                <#list categories as category>
                                    <option value="${category.id?c}"
                                        <#if resumeDto.categoryId??
                                            && resumeDto.categoryId == category.id>
                                            selected
                                        </#if>>
                                        ${category.name}
                                    </option>
                                </#list>
                            </select>

                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="mb-4">
                            <label class="form-label" for="salary">
                                <@spring.message 'resume.form.salary'/>
                            </label>

                            <@spring.formInput "resumeDto.salary"
                                'id="salary" class="form-control" min="0.01" step="0.01" required'
                                "number"/>

                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <hr class="my-4">

                        <h2 class="h5 mb-3">
                            <@spring.message 'resume.form.contacts'/>
                        </h2>

                        <div class="row g-3 mb-4">
                            <#list contactTypes as contactType>
                                <#assign contactIndex = contactType?index>

                                <div class="col-12 col-md-6">
                                    <label class="form-label d-block">
                                        ${contactType.type}

                                        <input type="hidden"
                                               name="contactInfo[${contactIndex?c}].typeId"
                                               value="${contactType.id?c}">

                                        <@spring.formInput
                                            "resumeDto.contactInfo[${contactIndex?c}].value"
                                            'class="form-control" maxlength="255"'/>
                                    </label>

                                    <@spring.showErrors "<br>" "text-danger small"/>
                                </div>
                            </#list>
                        </div>

                        <hr class="my-4">

                        <h2 class="h5 mb-3">
                            <@spring.message 'resume.form.work.title'/>
                        </h2>

                        <div id="experience-container">
                            <#list resumeDto.workExperienceInfo as work>
                                <#assign index = work?index>

                                <div class="card border mb-3 resume-detail-item">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-end mb-3">
                                            <button type="button"
                                                    class="btn btn-outline-danger btn-sm"
                                                    data-remove-detail>
                                                ${removeLabel}
                                            </button>
                                        </div>

                                        <div class="row g-3">
                                            <div class="col-12 col-md-6">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.work.company'/>
                                                    <@spring.formInput
                                                        "resumeDto.workExperienceInfo[${index?c}].companyName"
                                                        'class="form-control" minlength="2" maxlength="255" required'/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-6">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.work.position'/>
                                                    <@spring.formInput
                                                        "resumeDto.workExperienceInfo[${index?c}].position"
                                                        'class="form-control" minlength="2" maxlength="255" required'/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-4">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.work.years'/>
                                                    <@spring.formInput
                                                        "resumeDto.workExperienceInfo[${index?c}].years"
                                                        'class="form-control" min="0" max="70" required'
                                                        "number"/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>

                                            <div class="col-12">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.work.responsibilities'/>
                                                    <@spring.formTextarea
                                                        "resumeDto.workExperienceInfo[${index?c}].responsibilities"
                                                        'class="form-control" rows="3" maxlength="2000" required'/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>

                        <button type="button"
                                id="add-experience"
                                class="btn btn-outline-primary mb-4">
                            + ${addWorkLabel}
                        </button>

                        <hr class="my-4">

                        <h2 class="h5 mb-3">
                            <@spring.message 'resume.form.education.title'/>
                        </h2>

                        <div id="education-container">
                            <#list resumeDto.educationInfo as education>
                                <#assign index = education?index>

                                <div class="card border mb-3 resume-detail-item">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-end mb-3">
                                            <button type="button"
                                                    class="btn btn-outline-danger btn-sm"
                                                    data-remove-detail>
                                                ${removeLabel}
                                            </button>
                                        </div>

                                        <div class="row g-3">
                                            <div class="col-12 col-md-6">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.education.institution'/>
                                                    <@spring.formInput
                                                        "resumeDto.educationInfo[${index?c}].institution"
                                                        'class="form-control" minlength="2" maxlength="255" required'/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-6">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.education.program'/>
                                                    <@spring.formInput
                                                        "resumeDto.educationInfo[${index?c}].program"
                                                        'class="form-control" minlength="2" maxlength="255" required'/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-4">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.education.startDate'/>
                                                    <@spring.formInput
                                                        "resumeDto.educationInfo[${index?c}].startDate"
                                                        'class="form-control" required'
                                                        "date"/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-4">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.education.endDate'/>
                                                    <@spring.formInput
                                                        "resumeDto.educationInfo[${index?c}].endDate"
                                                        'class="form-control"'
                                                        "date"/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>

                                            <div class="col-12 col-md-4">
                                                <label class="form-label d-block">
                                                    <@spring.message 'resume.form.education.degree'/>
                                                    <@spring.formInput
                                                        "resumeDto.educationInfo[${index?c}].degree"
                                                        'class="form-control" minlength="2" maxlength="100" required'/>
                                                </label>
                                                <div class="field-errors">
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>

                        <button type="button"
                                id="add-education"
                                class="btn btn-outline-primary mb-4">
                            + ${addEducationLabel}
                        </button>

                        <div class="form-check form-switch mb-4">
                            <@spring.formCheckbox "resumeDto.isActive"
                                'id="isActive" class="form-check-input"'/>

                            <label class="form-check-label" for="isActive">
                                <@spring.message 'resume.form.publish'/>
                            </label>

                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="d-flex gap-2">
                            <button class="btn btn-primary" type="submit">
                                <#if formMode == "create">
                                    <@spring.message 'resume.form.create.submit'/>
                                <#else>
                                    <@spring.message 'resume.form.edit.submit'/>
                                </#if>
                            </button>

                            <a class="btn btn-outline-secondary" href="/profile">
                                <@spring.message 'resume.form.cancel'/>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="/js/resume-form.js" defer></script>
</@layout.layout>
