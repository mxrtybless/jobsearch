<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle>
    <@spring.message 'auth.register.title'/>
</#assign>

<#assign userNameLabel>
    <@spring.message 'auth.register.name.user'/>
</#assign>

<#assign companyNameLabel>
    <@spring.message 'auth.register.name.company'/>
</#assign>

<#assign applicantAgeLabel>
    <@spring.message 'auth.register.age'/>
</#assign>

<#assign employerAgeLabel>
    <@spring.message 'auth.register.age.employer'/>
</#assign>


<@layout.layout title=pageTitle + " | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-lg-7">

            <div class="card shadow-sm border-0">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2">
                        <@spring.message 'auth.register.heading'/>
                    </h1>

                    <p class="text-secondary mb-4">
                        <@spring.message 'auth.register.description'/>
                    </p>


                    <#if avatarErrorKey??>

                        <div class="alert alert-danger">
                            <@spring.message avatarErrorKey/>
                        </div>

                    </#if>


                    <form
                            action="/auth/register"
                            method="post"
                            enctype="multipart/form-data">

                        <@layout.csrfInput/>


                        <div class="mb-4">

                            <label class="form-label fw-semibold">
                                <@spring.message 'auth.register.accountType'/>
                            </label>

                            <@spring.bind "userCreateDto.accountType"/>

                            <div class="d-flex gap-4">

                                <div class="form-check">

                                    <input
                                            class="form-check-input"
                                            type="radio"
                                            name="accountType"
                                            id="applicant"
                                            value="APPLICANT"
                                            <#if !userCreateDto.accountType??
                                            || userCreateDto.accountType?string == "APPLICANT">
                                                checked
                                            </#if>>

                                    <label
                                            class="form-check-label"
                                            for="applicant">

                                        <@spring.message
                                                'auth.register.accountType.applicant'/>

                                    </label>

                                </div>


                                <div class="form-check">

                                    <input
                                            class="form-check-input"
                                            type="radio"
                                            name="accountType"
                                            id="employer"
                                            value="EMPLOYER"
                                            <#if userCreateDto.accountType??
                                            && userCreateDto.accountType?string == "EMPLOYER">
                                                checked
                                            </#if>>

                                    <label
                                            class="form-check-label"
                                            for="employer">

                                        <@spring.message
                                                'auth.register.accountType.employer'/>

                                    </label>

                                </div>

                            </div>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    id="nameLabel"
                                    class="form-label"
                                    for="name">

                                <@spring.message 'auth.register.name.user'/>

                            </label>

                            <@spring.formInput
                                    "userCreateDto.name"
                                    'id="name" class="form-control" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div
                                class="mb-3"
                                id="surnameBlock">

                            <label
                                    class="form-label"
                                    for="surname">

                                <@spring.message 'auth.register.surname'/>

                            </label>

                            <@spring.formInput
                                    "userCreateDto.surname"
                                    'id="surname" class="form-control"'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    id="ageLabel"
                                    class="form-label"
                                    for="age">

                                <@spring.message 'auth.register.age'/>

                            </label>

                            <@spring.formInput
                                    "userCreateDto.age"
                                    'id="age" class="form-control" min="18" max="100" required'
                                    "number"/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="email">

                                <@spring.message 'auth.register.email'/>

                            </label>

                            <@spring.formInput
                                    "userCreateDto.email"
                                    'id="email" class="form-control" required'
                                    "email"/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="phoneNumber">

                                <@spring.message 'auth.register.phone'/>

                            </label>

                            <@spring.formInput
                                    "userCreateDto.phoneNumber"
                                    'id="phoneNumber" class="form-control" placeholder="+996700123456" required'/>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    class="form-label"
                                    for="password">

                                <@spring.message 'auth.register.password'/>

                            </label>

                            <@spring.formInput
                                    "userCreateDto.password"
                                    'id="password" class="form-control" required'
                                    "password"/>

                            <div class="form-text">
                                <@spring.message
                                        'auth.register.password.hint'/>
                            </div>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-4">

                            <label
                                    class="form-label"
                                    for="avatar">

                                <@spring.message 'auth.register.avatar'/>

                            </label>

                            <input
                                    id="avatar"
                                    class="form-control"
                                    type="file"
                                    name="avatar"
                                    accept="image/*">

                            <div class="form-text">
                                <@spring.message
                                        'auth.register.avatar.hint'/>
                            </div>

                        </div>


                        <button
                                class="btn btn-primary w-100"
                                type="submit">

                            <@spring.message 'auth.register.submit'/>

                        </button>

                    </form>


                    <p class="text-center mt-4 mb-0">

                        <@spring.message
                                'auth.register.haveAccount'/>

                        <a href="/auth/login">
                            <@spring.message 'auth.register.login'/>
                        </a>

                    </p>

                </div>

            </div>

        </div>

    </div>


    <script>
        const applicantRadio =
            document.getElementById("applicant");

        const employerRadio =
            document.getElementById("employer");

        const nameLabel =
            document.getElementById("nameLabel");

        const surnameBlock =
            document.getElementById("surnameBlock");

        const surnameInput =
            document.getElementById("surname");

        const ageLabel =
            document.getElementById("ageLabel");

        const userNameLabel =
            "${userNameLabel?trim?js_string}";

        const companyNameLabel =
            "${companyNameLabel?trim?js_string}";

        const applicantAgeLabel =
            "${applicantAgeLabel?trim?js_string}";

        const employerAgeLabel =
            "${employerAgeLabel?trim?js_string}";


        function updateRegistrationForm() {

            if (employerRadio.checked) {

                nameLabel.textContent =
                    companyNameLabel;

                ageLabel.textContent =
                    employerAgeLabel;

                surnameBlock.style.display =
                    "none";

                surnameInput.value =
                    "";

                surnameInput.required =
                    false;

            } else {

                nameLabel.textContent =
                    userNameLabel;

                ageLabel.textContent =
                    applicantAgeLabel;

                surnameBlock.style.display =
                    "block";

                surnameInput.required =
                    true;
            }
        }


        applicantRadio.addEventListener(
            "change",
            updateRegistrationForm
        );

        employerRadio.addEventListener(
            "change",
            updateRegistrationForm
        );

        updateRegistrationForm();
    </script>

</@layout.layout>