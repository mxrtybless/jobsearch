<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "profile.edit.title"
        )>

<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-lg-7">

            <div class="card border-0 shadow-sm">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-4">
                        <@spring.message 'profile.edit.heading'/>
                    </h1>

                    <form
                            action="/profile/edit"
                            method="post">

                        <@layout.csrfInput/>

                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="name">

                                <#if isApplicant>
                                    <@spring.message 'profile.name'/>
                                <#else>
                                    <@spring.message 'profile.companyName'/>
                                </#if>

                            </label>

                            <@spring.formInput
                                    "profileUpdateDto.name"
                                    'id="name" class="form-control" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <#if isApplicant>

                            <div class="mb-3">

                                <label
                                        class="form-label"
                                        for="surname">

                                    <@spring.message 'profile.surname'/>

                                </label>

                                <@spring.formInput
                                        "profileUpdateDto.surname"
                                        'id="surname" class="form-control" required'/>

                                <@spring.showErrors
                                        "<br>"
                                        "text-danger small"/>

                            </div>

                        </#if>


                        <div class="mb-4">

                            <label
                                    class="form-label"
                                    for="age">

                                <@spring.message 'profile.age'/>

                            </label>

                            <@spring.formInput
                                    "profileUpdateDto.age"
                                    'id="age" class="form-control" min="18" max="100"'
                                    "number"/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="alert alert-secondary small">
                            <@spring.message 'profile.edit.note'/>
                        </div>


                        <div class="d-flex gap-2">

                            <button
                                    class="btn btn-primary"
                                    type="submit">

                                <@spring.message 'profile.edit.save'/>

                            </button>

                            <a
                                    class="btn btn-outline-secondary"
                                    href="/profile">

                                <@spring.message 'profile.edit.cancel'/>

                            </a>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>

</@layout.layout>