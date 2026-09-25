document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("vacancy-filter-form");
    if (!form) return;
    const key = "jobsearch.vacancy.filter";
    const names = ["name", "category", "salary", "experience", "sort"];
    const results = document.getElementById("vacancy-results");
    const error = document.getElementById("search-error");
    let timer;
    let version = 0;

    function state() {
        const value = {};
        names.forEach(name => value[name] = form.elements[name].value);
        return value;
    }

    function save() {
        try { localStorage.setItem(key, JSON.stringify(state())); } catch (e) {}
    }

    async function search(page) {
        clearTimeout(timer);
        const requestVersion = ++version;
        if (!form.reportValidity()) {
            results.removeAttribute("aria-busy");
            return;
        }
        save();
        const params = new URLSearchParams(state());
        params.set("page", String(page));
        const lang = new URL(window.location.href).searchParams.get("lang");
        if (lang) params.set("lang", lang);
        results.setAttribute("aria-busy", "true");
        error.classList.add("d-none");
        try {
            const response = await fetch("/vacancies/filter?" + params.toString());
            if (!response.ok) throw new Error("Search failed");
            const html = await response.text();
            if (requestVersion !== version) return;
            results.innerHTML = html;
            history.replaceState(null, "", "/vacancies?" + params.toString());
        } catch (e) {
            if (requestVersion === version) error.classList.remove("d-none");
        } finally {
            if (requestVersion === version) results.removeAttribute("aria-busy");
        }
    }

    form.addEventListener("submit", event => {
        event.preventDefault();
        search(1);
    });
    form.addEventListener("input", () => {
        ++version;
        clearTimeout(timer);
        timer = setTimeout(() => search(1), 300);
    });
    form.addEventListener("change", () => search(1));
    results.addEventListener("click", event => {
        const link = event.target.closest("a[data-page]");
        if (!link) return;
        event.preventDefault();
        search(Number(link.dataset.page));
    });
    document.getElementById("vacancy-filter-reset").addEventListener("click", () => {
        names.forEach(name => form.elements[name].value = name === "sort" ? "dateDesc" : "");
        search(1);
    });

    const url = new URL(window.location.href);
    if (!names.some(name => url.searchParams.has(name))) {
        try {
            const saved = JSON.parse(localStorage.getItem(key));
            if (saved && typeof saved === "object" && !Array.isArray(saved)) {
                names.forEach(name => {
                    if (typeof saved[name] === "string") form.elements[name].value = saved[name];
                });
                if (!form.elements.sort.value) form.elements.sort.value = "dateDesc";
                if (form.checkValidity()) search(1);
                else {
                    names.forEach(name => form.elements[name].value = name === "sort" ? "dateDesc" : "");
                    search(1);
                }
            }
        } catch (e) {}
    } else save();
});
