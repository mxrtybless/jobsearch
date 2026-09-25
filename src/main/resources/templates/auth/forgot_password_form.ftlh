<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=en?then("Password recovery", "Восстановление пароля")>
    <div class="card card-body mx-auto" style="max-width: 600px">
        <h1 class="h3">${en?then("Password recovery", "Восстановление пароля")}</h1>
        <#if demo><p class="alert alert-warning">${en?then("Lab mode: the token is displayed here instead of being emailed. Do not use this mode on a public site.", "Учебный режим: токен показан на странице вместо письма. Этот режим не предназначен для публичного сайта.")}</p></#if>
        <#if errorKey??><p class="alert alert-danger"><@spring.message errorKey/></p></#if>
        <#if messageKey??><p class="alert alert-success"><@spring.message messageKey/></p></#if>
        <#if invalidEmail!false><p class="text-danger">${en?then("Enter a valid email.", "Введите корректный email.")}</p></#if>
        <form action="/auth/forgot_password" method="post">
            <@layout.csrfInput/>
            <label for="email" class="form-label">Email</label>
            <input type="email" id="email" name="email" class="form-control" value="${recovery.email!}" maxlength="255" required>
            <button class="btn btn-primary mt-3">${en?then("Request token", "Получить токен")}</button>
        </form>
        <#if demoToken??>
            <p class="mt-3">${en?then("Your token (valid for 30 minutes)", "Ваш токен (действует 30 минут)")}: <code id="demo-token">${demoToken}</code></p>
            <form action="/auth/reset_password" method="get">
                <label for="token" class="form-label">${en?then("Enter the token", "Введите токен")}</label>
                <input id="token" name="token" class="form-control" maxlength="255" autocomplete="off" required>
                <button class="btn btn-primary mt-3">${en?then("Continue", "Продолжить")}</button>
            </form>
        </#if>
        <a class="mt-3" href="/auth/login">${en?then("Back to login", "Вернуться ко входу")}</a>
    </div>
</@layout.layout>
