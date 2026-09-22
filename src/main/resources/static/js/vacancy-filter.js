(function () {
    "use strict";

    const STORAGE_KEY = "jobsearch.vacancy.filter";

    const form = document.getElementById("vacancy-filter-form");

    if (!form) {
        return;
    }

    const fields = {
        name: document.getElementById("vacancy-name-filter"),
        category: document.getElementById("vacancy-category-filter"),
        salary: document.getElementById("vacancy-salary-filter"),
        experience: document.getElementById("vacancy-experience-filter")
    };

    const resetButton =
        document.getElementById("vacancy-filter-reset");

    const resultCount =
        document.getElementById("vacancy-filter-result-count");

    const emptyMessage =
        document.getElementById("vacancy-filter-empty");

    function readState() {
        try {
            const savedState = localStorage.getItem(STORAGE_KEY);

            if (!savedState) {
                return {};
            }

            return JSON.parse(savedState) || {};
        } catch (error) {
            return {};
        }
    }

    function saveState(state) {
        try {
            localStorage.setItem(
                STORAGE_KEY,
                JSON.stringify(state)
            );
        } catch (error) {
        }
    }

    function getStateFromForm() {
        return {
            name: fields.name.value.trim().toLowerCase(),
            category: fields.category.value,
            salary: fields.salary.value.trim(),
            experience: fields.experience.value.trim()
        };
    }

    function restoreState() {
        const state = readState();

        fields.name.value = state.name || "";
        fields.category.value = state.category || "";
        fields.salary.value = state.salary || "";
        fields.experience.value = state.experience || "";
    }

    function parseNumber(value) {
        if (value === null || value === undefined) {
            return null;
        }

        const normalizedValue = String(value).trim();

        if (!normalizedValue) {
            return null;
        }

        const number = Number(normalizedValue);

        return Number.isFinite(number)
                ? number
                : null;
    }

    function vacancyMatches(card, state) {
        const vacancyName =
                (card.dataset.vacancyName || "").toLowerCase();

        if (
                state.name
                && !vacancyName.includes(state.name)
        ) {
            return false;
        }

        if (
                state.category
                && card.dataset.vacancyCategory !== state.category
        ) {
            return false;
        }

        const minimumSalary =
                parseNumber(state.salary);

        if (minimumSalary !== null) {
            const vacancySalary =
                    parseNumber(card.dataset.vacancySalary);

            if (
                    vacancySalary === null
                    || vacancySalary < minimumSalary
            ) {
                return false;
            }
        }

        const candidateExperience =
                parseNumber(state.experience);

        if (candidateExperience !== null) {
            const experienceFrom =
                    parseNumber(card.dataset.vacancyExpFrom);

            const experienceTo =
                    parseNumber(card.dataset.vacancyExpTo);

            if (
                    experienceFrom === null
                    || experienceTo === null
                    || candidateExperience < experienceFrom
                    || candidateExperience > experienceTo
            ) {
                return false;
            }
        }

        return true;
    }

    function applyFilter(saveCurrentState) {
        const state = getStateFromForm();
        const cards = document.querySelectorAll(".vacancy-card");

        if (saveCurrentState) {
            saveState(state);
        }

        let visibleCount = 0;

        cards.forEach(function (card) {
            const matches = vacancyMatches(card, state);

            card.classList.toggle("d-none", !matches);
            card.classList.toggle(
                    "vacancy-card-hidden",
                    !matches
            );

            if (matches) {
                visibleCount += 1;
            }
        });

        if (resultCount) {
            resultCount.textContent =
                    visibleCount + " / " + cards.length;
        }

        if (emptyMessage) {
            emptyMessage.classList.toggle(
                    "d-none",
                    cards.length === 0 || visibleCount > 0
            );
        }
    }

    function saveCurrentFilter() {
        saveState(getStateFromForm());
    }

    form.addEventListener("input", function () {
        applyFilter(true);
    });

    form.addEventListener("change", function () {
        applyFilter(true);
    });

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        applyFilter(true);
    });

    if (resetButton) {
        resetButton.addEventListener("click", function () {
            form.reset();

            try {
                localStorage.removeItem(STORAGE_KEY);
            } catch (error) {
            }

            applyFilter(true);
        });
    }

    document
            .querySelectorAll('form[action="/vacancies"]')
            .forEach(function (sortForm) {
                sortForm.addEventListener(
                        "submit",
                        saveCurrentFilter
                );
            });

    document
            .querySelectorAll('a[href^="/vacancies?"]')
            .forEach(function (link) {
                link.addEventListener(
                        "click",
                        saveCurrentFilter
                );
            });

    window.addEventListener(
            "pagehide",
            saveCurrentFilter
    );

    window.addEventListener(
            "pageshow",
            function () {
                restoreState();
                applyFilter(false);
            }
    );

    window.addEventListener(
            "storage",
            function (event) {
                if (
                        event.key === STORAGE_KEY
                        || event.key === null
                ) {
                    restoreState();
                    applyFilter(false);
                }
            }
    );

    restoreState();
    applyFilter(false);
})();