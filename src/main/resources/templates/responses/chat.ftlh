<#import "../layout.ftlh" as layout>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=en?then("Chat", "Переписка")>
    <h1 class="h3">${discussion.vacancy.name}</h1>
    <p>${discussion.vacancy.author.name} · <a href="/resumes/${discussion.resume.id?c}">${discussion.resume.applicant.name} — ${discussion.resume.name}</a></p>
    <a class="btn btn-outline-secondary mb-3" href="/responses/${discussion.id?c}/chat">${en?then("Refresh messages", "Обновить сообщения")}</a>
    <section aria-label="${en?then('Messages', 'Сообщения')}">
        <#list messages as item>
            <article class="card card-body mb-2 <#if item.sender?? && item.sender.email == viewerEmail>border-primary</#if>">
                <strong><#if item.sender??>${item.sender.name}<#else>${en?then("Previous message", "Старое сообщение")}</#if></strong>
                <small class="text-secondary">${item.timestamp!}</small>
                <p class="mb-0" style="white-space: pre-wrap; overflow-wrap: anywhere">${item.content}</p>
            </article>
        <#else><p>${en?then("Start the conversation.", "Начните переписку.")}</p></#list>
    </section>
    <#if invalidMessage!false><p class="alert alert-danger">${en?then("Enter 1–2000 characters, not just spaces.", "Введите от 1 до 2000 символов, не только пробелы.")}</p></#if>
    <form action="/responses/${discussion.id?c}/chat" method="post" class="mt-3">
        <@layout.csrfInput/>
        <label class="form-label" for="content">${en?then("Message", "Сообщение")}</label>
        <textarea id="content" name="content" class="form-control" rows="3" maxlength="2000" required>${messageForm.content!}</textarea>
        <button class="btn btn-primary mt-2">${en?then("Send", "Отправить")}</button>
    </form>
</@layout.layout>
