document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("resume-form");

    if (!form) {
        return;
    }

    function setupDetails(containerId, addButtonId, fieldName) {
        const container = document.getElementById(containerId);
        const addButton = document.getElementById(addButtonId);

        if (!container || !addButton) {
            return;
        }

        function getItems() {
            return Array.from(
                container.querySelectorAll(".resume-detail-item")
            );
        }

        function updateIndexes() {
            const items = getItems();

            items.forEach((item, index) => {
                item.querySelectorAll("[name]").forEach(field => {
                    const property = field.name.substring(
                        field.name.indexOf("].") + 2
                    );

                    field.name = `${fieldName}[${index}].${property}`;
                    field.id = `${fieldName}-${index}-${property}`;
                });

                const removeButton = item.querySelector(
                    "[data-remove-detail]"
                );

                removeButton.disabled = items.length === 1;
            });
        }

        addButton.addEventListener("click", () => {
            const firstItem = getItems()[0];

            if (!firstItem) {
                return;
            }

            const newItem = firstItem.cloneNode(true);

            newItem.querySelectorAll("input, textarea").forEach(field => {
                field.value = "";
                field.defaultValue = "";
                field.classList.remove("is-invalid");
            });

            newItem.querySelectorAll(".field-errors").forEach(errors => {
                errors.textContent = "";
            });

            container.appendChild(newItem);
            updateIndexes();

            newItem.querySelector("input, textarea")?.focus();
        });

        container.addEventListener("click", event => {
            const removeButton = event.target.closest(
                "[data-remove-detail]"
            );

            if (!removeButton || getItems().length <= 1) {
                return;
            }

            removeButton.closest(".resume-detail-item").remove();
            updateIndexes();
        });

        form.addEventListener("submit", updateIndexes);

        updateIndexes();
    }

    setupDetails(
        "experience-container",
        "add-experience",
        "workExperienceInfo"
    );

    setupDetails(
        "education-container",
        "add-education",
        "educationInfo"
    );
});