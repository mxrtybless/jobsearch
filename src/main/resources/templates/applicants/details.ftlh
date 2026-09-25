<#import "../layout.ftlh" as layout>
<#assign en = .locale?starts_with("en")>
<@layout.layout title=applicant.name>
    <div class="card card-body mb-3">
        <#if applicant.avatar?has_content><img src="/images/download?filename=${applicant.avatar?url('UTF-8')}" alt="${applicant.name}" width="110" height="110" class="rounded-circle" style="object-fit: cover"></#if>
        <h1 class="h3">${applicant.name} ${applicant.surname!}</h1>
        <#if applicant.age??><p>${en?then("Age", "Возраст")}: ${applicant.age}</p></#if>
    </div>
    <h2 class="h4">${en?then("Published resumes", "Опубликованные резюме")}</h2>
    <#list resumes as resume><article class="card card-body mb-3"><a href="/resumes/${resume.id?c}">${resume.name}</a><p>${resume.salary!}</p></article>
    <#else><p>${en?then("No published resumes.", "Опубликованных резюме нет.")}</p></#list>
    <@layout.pagination currentPage=currentPage totalPages=totalPages baseUrl="/applicants/${applicant.id?c}"/>
</@layout.layout>
