<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=en?then("New password", "Новый пароль")>
    <div class="card card-body mx-auto" style="max-width: 600px">
        <h1 class="h3">${en?then("New password", "Новый пароль")}</h1>
        <#if invalidToken!false>
            <p class="alert alert-danger">${en?then("The token is invalid, expired or already used.", "Токен неверный, просрочен или уже использован.")}</p>
            <a href="/auth/forgot_password">${en?then("Request another token", "Получить новый токен")}</a>
        <#else>
            <form action="/auth/reset_password" method="post">
                <@layout.csrfInput/>
                <input type="hidden" name="token" value="${passwordResetDto.token}">
                <label class="form-label" for="password">${en?then("Password", "Пароль")}</label>
                <input type="password" name="password" id="password" class="form-control" minlength="8" maxlength="32" autocomplete="new-password" required>
                <p class="form-text"><@spring.message 'auth.register.password.hint'/></p>
                <@spring.bind "passwordResetDto.password"/><@spring.showErrors "<br>" "text-danger"/>
                <button class="btn btn-primary mt-3">${en?then("Save password", "Сохранить пароль")}</button>
            </form>
        </#if>
    </div>
</@layout.layout>
