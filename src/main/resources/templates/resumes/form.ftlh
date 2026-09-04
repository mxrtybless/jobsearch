<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#if formMode == "create">

    <#assign pageTitle =
            springMacroRequestContext.getMessage(
                    "resume.form.create.title"
            )>

    <#assign formAction = "/resumes/form/create">

<#else>

    <#assign pageTitle =
            springMacroRequestContext.getMessage(
                    "resume.form.edit.title"
            )>

    <#assign formAction =
            "/resumes/form/edit/" + resumeId?c>

</#if>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-xl-9">

            <div class="card border-0 shadow-sm">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2">

                        <#if formMode == "create">

                            <@spring.message
                                    'resume.form.create.heading'/>

                        <#else>

                            <@spring.message
                                    'resume.form.edit.heading'/>

                        </#if>

                    </h1>


                    <p class="text-secondary mb-4">
                        <@spring.message 'resume.form.description'/>
                    </p>


                    <@spring.bind "resumeDto.*"/>

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

                                <@spring.message 'resume.form.name'/>

                            </label>

                            <@spring.formInput
                                    "resumeDto.name"
                                    'id="name" class="form-control" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="categoryId">

                                <@spring.message 'resume.form.category'/>

                            </label>

                            <@spring.bind "resumeDto.categoryId"/>

                            <select
                                    id="categoryId"
                                    class="form-select"
                                    name="categoryId"
                                    required>

                                <option value="">
                                    <@spring.message
                                            'resume.form.category.select'/>
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

                            <label
                                    class="form-label"
                                    for="salary">

                                <@spring.message 'resume.form.salary'/>

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
                            <@spring.message 'resume.form.contacts'/>
                        </h2>


                        <div class="row g-3 mb-4">

                            <#list contactTypes as contactType>

                                <#assign contactIndex =
                                        contactType?index>

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
                                            'class="form-control"'/>

                                    <@spring.showErrors
                                            "<br>"
                                            "text-danger small"/>

                                </div>

                            </#list>

                        </div>


                        <hr class="my-4">


                        <h2 class="h5 mb-3">
                            <@spring.message 'resume.form.work.title'/>
                        </h2>


                        <div class="card border mb-4">

                            <div class="card-body">

                                <div class="row g-3">

                                    <div class="col-12 col-md-6">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.work.company'/>
                                        </label>

                                        <@spring.formInput
                                                "resumeDto.workExperienceInfo[0].companyName"
                                                'class="form-control" required'/>

                                    </div>


                                    <div class="col-12 col-md-6">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.work.position'/>
                                        </label>

                                        <@spring.formInput
                                                "resumeDto.workExperienceInfo[0].position"
                                                'class="form-control" required'/>

                                    </div>


                                    <div class="col-12 col-md-4">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.work.years'/>
                                        </label>

                                        <@spring.formInput
                                                "resumeDto.workExperienceInfo[0].years"
                                                'class="form-control" min="0" max="70" required'
                                                "number"/>

                                    </div>


                                    <div class="col-12">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.work.responsibilities'/>
                                        </label>

                                        <@spring.formTextarea
                                                "resumeDto.workExperienceInfo[0].responsibilities"
                                                'class="form-control" rows="3" required'/>

                                    </div>

                                </div>

                            </div>

                        </div>


                        <hr class="my-4">


                        <h2 class="h5 mb-3">
                            <@spring.message
                                    'resume.form.education.title'/>
                        </h2>


                        <div class="card border mb-4">

                            <div class="card-body">

                                <div class="row g-3">

                                    <div class="col-12 col-md-6">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.education.institution'/>
                                        </label>

                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].institution"
                                                'class="form-control" required'/>

                                    </div>


                                    <div class="col-12 col-md-6">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.education.program'/>
                                        </label>

                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].program"
                                                'class="form-control" required'/>

                                    </div>


                                    <div class="col-12 col-md-4">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.education.startDate'/>
                                        </label>

                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].startDate"
                                                'class="form-control" required'
                                                "date"/>

                                    </div>


                                    <div class="col-12 col-md-4">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.education.endDate'/>
                                        </label>

                                        <@spring.formInput
                                                "resumeDto.educationInfo[0].endDate"
                                                'class="form-control"'
                                                "date"/>

                                    </div>


                                    <div class="col-12 col-md-4">

                                        <label class="form-label">
                                            <@spring.message
                                                    'resume.form.education.degree'/>
                                        </label>

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

                            <label
                                    class="form-check-label"
                                    for="isActive">

                                <@spring.message 'resume.form.publish'/>

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
                                            'resume.form.create.submit'/>

                                <#else>

                                    <@spring.message
                                            'resume.form.edit.submit'/>

                                </#if>

                            </button>


                            <a
                                    class="btn btn-outline-secondary"
                                    href="/profile">

                                <@spring.message 'resume.form.cancel'/>

                            </a>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>