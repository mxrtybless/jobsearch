document.addEventListener("DOMContentLoaded", () => {
    const languageKey = "jobsearch.language";
    const supportedLanguages = ["ru", "en"];
    const currentLanguage = document.documentElement.lang;
    const currentUrl = new URL(window.location.href);

    function saveLanguage(language) {
        try {
            localStorage.setItem(languageKey, language);
        } catch (error) {
            // Если хранилище недоступно, остаётся серверная cookie.
        }
    }

    function restoreLanguage() {
        try {
            return localStorage.getItem(languageKey);
        } catch (error) {
            return null;
        }
    }

    const requestedLanguage = currentUrl.searchParams.get("lang");

    if (supportedLanguages.includes(requestedLanguage)) {
        saveLanguage(requestedLanguage);
    } else {
        const savedLanguage = restoreLanguage();

        if (supportedLanguages.includes(savedLanguage)) {
            if (savedLanguage !== currentLanguage) {
                currentUrl.searchParams.set("lang", savedLanguage);
                window.location.replace(currentUrl.toString());
                return;
            }
        } else if (supportedLanguages.includes(currentLanguage)) {
            saveLanguage(currentLanguage);
        }
    }

    document.querySelectorAll("[data-language]").forEach(link => {
        const language = link.dataset.language;
        const destination = new URL(window.location.href);

        destination.searchParams.set("lang", language);
        link.href = destination.toString();

        link.addEventListener("click", () => {
            saveLanguage(language);
        });
    });

    const token = document.querySelector(
        'meta[name="_csrf"]'
    )?.content;

    const parameterName = document.querySelector(
        'meta[name="_csrf_parameter"]'
    )?.content;

    if (!token || !parameterName) {
        return;
    }

    document.querySelectorAll(
        'form[enctype="multipart/form-data"]'
    ).forEach(form => {
        if (form.method.toLowerCase() !== "post") {
            return;
        }

        const action = new URL(form.action, window.location.href);

        if (action.origin !== window.location.origin) {
            return;
        }

        action.searchParams.set(parameterName, token);
        form.action = action.toString();
    });
});