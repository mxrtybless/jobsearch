<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "profile.title"
        )>

<#assign avatarAlt =
        springMacroRequestContext.getMessage(
                "profile.avatar.alt"
        )>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="card border-0 shadow-sm mb-4">

        <div class="card-body p-4">

            <div class="d-flex flex-column flex-md-row gap-4 align-items-md-start">

                <#if user.avatar?? && user.avatar?has_content>

                    <img
                            src="/images/download?filename=${user.avatar?url}"
                            alt="${avatarAlt}"
                            class="rounded-circle border"
                            width="110"
                            height="110"
                            style="object-fit: cover">

                </#if>


                <div class="flex-grow-1">

                    <h1 class="h3">
                        ${user.name!}

                        <#if user.surname?? && user.surname?has_content>
                            ${user.surname}
                        </#if>
                    </h1>


                    <p class="mb-1">
                        <strong>Email:</strong>
                        ${user.email!}
                    </p>


                    <p class="mb-1">

                        <strong>
                            <@spring.message 'profile.phone'/>:
                        </strong>

                        ${user.phoneNumber!}

                    </p>


                    <#if user.age??>

                        <p>

                            <strong>
                                <@spring.message 'profile.age'/>:
                            </strong>

                            ${user.age}

                        </p>

                    </#if>


                    <div class="d-flex flex-wrap gap-2">

                        <button
                                class="btn btn-primary"
                                type="button"
                                data-bs-toggle="modal"
                                data-bs-target="#editProfileModal">

                            <@spring.message 'profile.edit'/>

                        </button>


                        <form
                                action="/profile/avatar"
                                method="post"
                                enctype="multipart/form-data"
                                class="d-flex gap-2">

                            <@layout.csrfInput/>

                            <input
                                    class="form-control"
                                    type="file"
                                    name="avatar"
                                    accept="image/*"
                                    required>

                            <button
                                    class="btn btn-outline-primary"
                                    type="submit">

                                <@spring.message 'profile.avatar.update'/>

                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </div>

    </div>


    <div
            class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-3">

        <div>

            <h2 class="h4 mb-1">

                <#if isApplicant>

                    <@spring.message 'profile.section.resumes'/>

                <#else>

                    <@spring.message 'profile.section.vacancies'/>

                </#if>

            </h2>

        </div>


        <div class="d-flex gap-2">

            <form
                    method="get"
                    action="/profile">

                <select
                        class="form-select"
                        name="sort"
                        onchange="this.form.submit()">

                    <option
                            value="dateDesc"
                            <#if (sort!"dateDesc") == "dateDesc">
                                selected
                            </#if>>

                        <@spring.message 'profile.sort.dateDesc'/>

                    </option>

                    <option
                            value="dateAsc"
                            <#if (sort!"dateDesc") == "dateAsc">
                                selected
                            </#if>>

                        <@spring.message 'profile.sort.dateAsc'/>

                    </option>

                    <option
                            value="responsesDesc"
                            <#if (sort!"dateDesc") == "responsesDesc">
                                selected
                            </#if>>

                        <@spring.message 'profile.sort.responsesDesc'/>

                    </option>

                    <option
                            value="responsesAsc"
                            <#if (sort!"dateDesc") == "responsesAsc">
                                selected
                            </#if>>

                        <@spring.message 'profile.sort.responsesAsc'/>

                    </option>

                </select>

                <input
                        type="hidden"
                        name="page"
                        value="1">

            </form>


            <#if isApplicant>

                <a
                        class="btn btn-primary"
                        href="/resumes/form/create">

                    <@spring.message 'profile.resume.create'/>

                </a>

            </#if>


            <#if isEmployer>

                <a
                        class="btn btn-primary"
                        href="/vacancies/form/create">

                    <@spring.message 'profile.vacancy.create'/>

                </a>

            </#if>

        </div>

    </div>


    <#if isApplicant>

        <#if resumes?? && resumes?size gt 0>

            <div class="row g-3">

                <#list resumes as resume>

                    <div class="col-12 col-md-6">

                        <div class="card h-100 border-0 shadow-sm">

                            <div class="card-body">

                                <div class="d-flex justify-content-between gap-2">

                                    <h3 class="h5">
                                        ${resume.name!}
                                    </h3>


                                    <#if resume.isActive?? && resume.isActive>

                                        <span class="badge text-bg-success">
                                            <@spring.message
                                                    'profile.status.published'/>
                                        </span>

                                    <#else>

                                        <span class="badge text-bg-secondary">
                                            <@spring.message
                                                    'profile.status.hidden'/>
                                        </span>

                                    </#if>

                                </div>


                                <p>

                                    <strong>
                                        <@spring.message 'profile.salary'/>:
                                    </strong>

                                    ${resume.salary!}

                                </p>


                                <p class="small text-secondary">

                                    <@spring.message 'profile.updated'/>:

                                    <#if resume.updateTime??>
                                        ${resume.updateTime}
                                    <#elseif resume.createdDate??>
                                        ${resume.createdDate}
                                    <#else>
                                        —
                                    </#if>

                                </p>


                                <div class="d-flex gap-2">

                                    <a
                                            class="btn btn-outline-primary btn-sm"
                                            href="/resumes/form/edit/${resume.id?c}">

                                        <@spring.message 'profile.item.edit'/>

                                    </a>


                                    <form
                                            method="post"
                                            action="/resumes/form/update/${resume.id?c}">

                                        <@layout.csrfInput/>

                                        <button
                                                class="btn btn-outline-secondary btn-sm"
                                                type="submit">

                                            <@spring.message
                                                    'profile.item.refresh'/>

                                        </button>

                                    </form>

                                </div>

                            </div>

                        </div>

                    </div>

                </#list>

            </div>


        <#else>

            <div class="alert alert-light">
                <@spring.message 'profile.resumes.empty'/>
            </div>

        </#if>


    <#else>

        <#if vacancies?? && vacancies?size gt 0>

            <div class="row g-3">

                <#list vacancies as vacancy>

                    <div class="col-12 col-md-6">

                        <div class="card h-100 border-0 shadow-sm">

                            <div class="card-body">

                                <div class="d-flex justify-content-between gap-2">

                                    <h3 class="h5">
                                        ${vacancy.name!}
                                    </h3>


                                    <#if vacancy.isActive?? && vacancy.isActive>

                                        <span class="badge text-bg-success">
                                            <@spring.message
                                                    'profile.status.published'/>
                                        </span>

                                    <#else>

                                        <span class="badge text-bg-secondary">
                                            <@spring.message
                                                    'profile.status.hidden'/>
                                        </span>

                                    </#if>

                                </div>


                                <p>

                                    <strong>
                                        <@spring.message 'profile.salary'/>:
                                    </strong>

                                    ${vacancy.salary!}

                                </p>


                                <p class="small text-secondary">

                                    <@spring.message 'profile.updated'/>:

                                    <#if vacancy.updateTime??>
                                        ${vacancy.updateTime}
                                    <#elseif vacancy.createdDate??>
                                        ${vacancy.createdDate}
                                    <#else>
                                        —
                                    </#if>

                                </p>


                                <div class="d-flex gap-2">

                                    <a
                                            class="btn btn-outline-primary btn-sm"
                                            href="/vacancies/form/edit/${vacancy.id?c}">

                                        <@spring.message 'profile.item.edit'/>

                                    </a>


                                    <form
                                            method="post"
                                            action="/vacancies/form/update/${vacancy.id?c}">

                                        <@layout.csrfInput/>

                                        <button
                                                class="btn btn-outline-secondary btn-sm"
                                                type="submit">

                                            <@spring.message
                                                    'profile.item.refresh'/>

                                        </button>

                                    </form>

                                </div>

                            </div>

                        </div>

                    </div>

                </#list>

            </div>


        <#else>

            <div class="alert alert-light">
                <@spring.message 'profile.vacancies.empty'/>
            </div>

        </#if>

    </#if>


    <@layout.pagination
            currentPage=currentPage!1
            totalPages=totalPages!0
            baseUrl="/profile"
            sort=sort!"dateDesc"/>


    <div
            class="modal fade"
            id="editProfileModal"
            tabindex="-1"
            aria-hidden="true">

        <div class="modal-dialog">

            <div class="modal-content">

                <form
                        action="/profile/edit"
                        method="post">

                    <@layout.csrfInput/>


                    <div class="modal-header">

                        <h5 class="modal-title">
                            <@spring.message 'profile.modal.title'/>
                        </h5>

                        <button
                                type="button"
                                class="btn-close"
                                data-bs-dismiss="modal"
                                aria-label="Close">
                        </button>

                    </div>


                    <div class="modal-body">

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="profileName">

                                <#if isApplicant>
                                    <@spring.message 'profile.name'/>
                                <#else>
                                    <@spring.message 'profile.companyName'/>
                                </#if>

                            </label>

                            <input
                                    id="profileName"
                                    class="form-control"
                                    name="name"
                                    value="${user.name!}"
                                    required>

                        </div>


                        <#if isApplicant>

                            <div class="mb-3">

                                <label
                                        class="form-label"
                                        for="profileSurname">

                                    <@spring.message 'profile.surname'/>

                                </label>

                                <input
                                        id="profileSurname"
                                        class="form-control"
                                        name="surname"
                                        value="${user.surname!}"
                                        required>

                            </div>

                        </#if>


                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="profileAge">

                                <@spring.message 'profile.age'/>

                            </label>

                            <input
                                    id="profileAge"
                                    class="form-control"
                                    type="number"
                                    name="age"
                                    value="${user.age!}">

                        </div>

                    </div>


                    <div class="modal-footer">

                        <button
                                class="btn btn-secondary"
                                type="button"
                                data-bs-dismiss="modal">

                            <@spring.message 'profile.modal.cancel'/>

                        </button>

                        <button
                                class="btn btn-primary"
                                type="submit">

                            <@spring.message 'profile.modal.save'/>

                        </button>

                    </div>

                </form>

            </div>

        </div>

    </div>

</@layout.layout>