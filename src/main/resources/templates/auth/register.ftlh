<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.layout title="Регистрация | JobSearch">

    <div class="row justify-content-center">

        <div class="col-12 col-lg-7">

            <div class="card shadow-sm border-0">

                <div class="card-body p-4 p-md-5">

                    <h1 class="h3 mb-2">
                        Регистрация
                    </h1>

                    <p class="text-secondary mb-4">
                        Выберите тип учётной записи
                        и заполните данные
                    </p>

                    <#if avatarError??>

                        <div class="alert alert-danger">
                            ${avatarError}
                        </div>

                    </#if>

                    <form
                            action="/auth/register"
                            method="post"
                            enctype="multipart/form-data">

                        <div class="mb-4">

                            <label class="form-label fw-semibold">
                                Тип учётной записи
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
                                        Соискатель
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
                                        Компания
                                    </label>

                                </div>

                            </div>

                            <@spring.showErrors "<br>" "text-danger small"/>

                        </div>


                        <div class="mb-3">

                            <label
                                    id="nameLabel"
                                    class="form-label"
                                    for="name">
                                Имя пользователя
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
                                Фамилия
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
                                Возраст
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
                                Email
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
                                Номер телефона
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
                                Пароль
                            </label>

                            <@spring.formInput
                                    "userCreateDto.password"
                                    'id="password" class="form-control" required'
                                    "password"/>

                            <div class="form-text">
                                8–32 символа, заглавная и строчная
                                буква и хотя бы одна цифра.
                            </div>

                            <@spring.showErrors
                                    "<br>"
                                    "text-danger small"/>

                        </div>


                        <div class="mb-4">

                            <label
                                    class="form-label"
                                    for="avatar">
                                Фото профиля
                            </label>

                            <input
                                    id="avatar"
                                    class="form-control"
                                    type="file"
                                    name="avatar"
                                    accept="image/*">

                            <div class="form-text">
                                Необязательно. Если фото не выбрать,
                                будет использовано изображение по умолчанию.
                            </div>

                        </div>


                        <button
                                class="btn btn-primary w-100"
                                type="submit">
                            Зарегистрироваться
                        </button>

                    </form>


                    <p class="text-center mt-4 mb-0">

                        Уже есть аккаунт?

                        <a href="/auth/login">
                            Войти
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

        function updateRegistrationForm() {
            if (employerRadio.checked) {
                nameLabel.textContent =
                    "Имя компании";

                ageLabel.textContent =
                    "Возраст представителя";

                surnameBlock.style.display =
                    "none";

                surnameInput.value =
                    "";
            } else {
                nameLabel.textContent =
                    "Имя пользователя";

                ageLabel.textContent =
                    "Возраст";

                surnameBlock.style.display =
                    "block";
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