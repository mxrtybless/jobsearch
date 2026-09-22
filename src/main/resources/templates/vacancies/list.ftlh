<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<#assign pageTitle =
        springMacroRequestContext.getMessage(
                "vacancy.list.title"
        )>

<@layout.layout title=pageTitle + " | JobSearch">

    <section
            id="vacancy-filter-panel"
            class="card border-0 shadow-sm mb-4">

        <div class="card-body p-4">

            <div class="mb-3">

                <h2 class="h5 mb-1">
                    Фильтр вакансий
                </h2>

                <p class="text-secondary mb-0">
                    Настройки фильтра сохраняются в браузере.
                </p>

            </div>

            <form id="vacancy-filter-form">

                <div class="row g-3">

                    <div class="col-12 col-md-6">

                        <label
                                for="vacancy-name-filter"
                                class="form-label">

                            Название вакансии

                        </label>

                        <input
                                id="vacancy-name-filter"
                                class="form-control"
                                type="search"
                                autocomplete="off"
                                placeholder="Например, Java-разработчик">

                    </div>

                    <div class="col-12 col-md-6">

                        <label
                                for="vacancy-category-filter"
                                class="form-label">

                            Категория

                        </label>

                        <select
                                id="vacancy-category-filter"
                                class="form-select">

                            <option value="">
                                Все категории
                            </option>

                            <#if categories??>

                                <#list categories as category>

                                    <option value="${category.id?c}">
                                        ${category.name!}
                                    </option>

                                </#list>

                            </#if>

                        </select>

                    </div>

                    <div class="col-12 col-md-6">

                        <label
                                for="vacancy-salary-filter"
                                class="form-label">

                            Минимальная зарплата

                        </label>

                        <input
                                id="vacancy-salary-filter"
                                class="form-control"
                                type="number"
                                min="0"
                                step="1"
                                placeholder="Например, 50000">

                    </div>

                    <div class="col-12 col-md-6">

                        <label
                                for="vacancy-experience-filter"
                                class="form-label">

                            Опыт кандидата, лет

                        </label>

                        <input
                                id="vacancy-experience-filter"
                                class="form-control"
                                type="number"
                                min="0"
                                max="70"
                                step="1"
                                placeholder="Например, 2">

                    </div>

                </div>

                <div
                        class="d-flex flex-wrap align-items-center gap-2 mt-4">

                    <button
                            class="btn btn-primary"
                            type="submit">

                        Применить

                    </button>

                    <button
                            id="vacancy-filter-reset"
                            class="btn btn-outline-secondary"
                            type="button">

                        Очистить

                    </button>

                    <span
                            id="vacancy-filter-result-count"
                            class="text-secondary small"
                            aria-live="polite">
                    </span>

                </div>

            </form>

        </div>

    </section>


    <div
            class="d-flex flex-column flex-md-row justify-content-between align-items-md-end gap-3 mb-4">

        <div>

            <h1 class="h2 mb-2">
                <@spring.message 'vacancy.list.heading'/>
            </h1>

            <p class="text-secondary mb-0">
                <@spring.message 'vacancy.list.description'/>
            </p>

        </div>


        <form
                method="get"
                action="/vacancies"
                class="d-flex gap-2">

            <select
                    class="form-select"
                    name="sort"
                    onchange="this.form.submit()">

                <option
                        value="dateDesc"
                        <#if (sort!"dateDesc") == "dateDesc">
                            selected
                        </#if>>

                    <@spring.message 'vacancy.sort.dateDesc'/>

                </option>

                <option
                        value="dateAsc"
                        <#if (sort!"dateDesc") == "dateAsc">
                            selected
                        </#if>>

                    <@spring.message 'vacancy.sort.dateAsc'/>

                </option>

                <option
                        value="responsesDesc"
                        <#if (sort!"dateDesc") == "responsesDesc">
                            selected
                        </#if>>

                    <@spring.message 'vacancy.sort.responsesDesc'/>

                </option>

                <option
                        value="responsesAsc"
                        <#if (sort!"dateDesc") == "responsesAsc">
                            selected
                        </#if>>

                    <@spring.message 'vacancy.sort.responsesAsc'/>

                </option>

            </select>

            <input
                    type="hidden"
                    name="page"
                    value="1">

        </form>

    </div>


    <#if vacancies?? && vacancies?size gt 0>

        <div class="d-flex flex-column gap-3">

            <#list vacancies as vacancy>

                <div
                        class="card border-0 shadow-sm list-card vacancy-card"
                        data-vacancy-name="${((vacancy.name!'')?lower_case)?html}"
                        data-vacancy-category="${(vacancy.categoryId!0)?c}"
                        data-vacancy-salary="${(vacancy.salary!0)?c}"
                        data-vacancy-exp-from="${(vacancy.expFrom!0)?c}"
                        data-vacancy-exp-to="${(vacancy.expTo!70)?c}">

                    <div class="card-body p-4">

                        <div class="d-flex justify-content-between gap-3">

                            <div>

                                <h2 class="h4">

                                    <a
                                            class="text-decoration-none"
                                            href="/vacancies/${vacancy.id?c}">

                                        ${vacancy.name!}

                                    </a>

                                </h2>

                                <p class="mb-2">

                                    <strong>
                                        <@spring.message 'vacancy.salary'/>:
                                    </strong>

                                    ${vacancy.salary!}

                                </p>

                                <p class="mb-2">

                                    <strong>
                                        <@spring.message 'vacancy.experience'/>:
                                    </strong>

                                    ${vacancy.expFrom!0}
                                    —
                                    ${vacancy.expTo!0}

                                    <@spring.message 'vacancy.years'/>

                                </p>

                                <p class="mb-0 text-secondary">
                                    ${vacancy.description!}
                                </p>

                            </div>


                            <div class="text-secondary small text-end">

                                <@spring.message 'vacancy.updated'/>

                                <br>

                                <strong class="text-dark">

                                    <#if vacancy.updateTime??>
                                        ${vacancy.updateTime}
                                    <#elseif vacancy.createdDate??>
                                        ${vacancy.createdDate}
                                    <#else>
                                        —
                                    </#if>

                                </strong>

                            </div>

                        </div>

                    </div>

                </div>

            </#list>

        </div>

        <div
                id="vacancy-filter-empty"
                class="alert alert-light d-none mt-3">

            Подходящих вакансий нет.

        </div>

    <#else>

        <div class="alert alert-light">
            <@spring.message 'vacancy.list.empty'/>
        </div>

    </#if>


    <@layout.pagination
            currentPage=currentPage!1
            totalPages=totalPages!0
            baseUrl="/vacancies"
            sort=sort!"dateDesc"
            hasContent=(vacancies?? && vacancies?size gt 0)/>


    <script
            src="/js/vacancy-filter.js"
            defer>
    </script>

</@layout.layout>