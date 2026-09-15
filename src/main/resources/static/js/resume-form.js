document.addEventListener("DOMContentLoaded", () => {

    const educationContainer = document.getElementById("education-container");
    const experienceContainer = document.getElementById("experience-container");

    let educationIndex = educationContainer
        ? educationContainer.querySelectorAll(".education-item").length
        : 0;

    let experienceIndex = experienceContainer
        ? experienceContainer.querySelectorAll(".experience-item").length
        : 0;

    const addEducation = document.getElementById("add-education");
    const addExperience = document.getElementById("add-experience");

    addEducation?.addEventListener("click", () => {
        const index = educationIndex++;

        educationContainer.insertAdjacentHTML("beforeend", `
        <div class="card border mb-4 education-item">
            <div class="card-body">
                <div class="row g-3">
                    <div class="col-md-6">
                        <input class="form-control"
                               name="educationInfo[${index}].institution"
                               placeholder="Учебное заведение" required>
                    </div>
                    <div class="col-md-6">
                        <input class="form-control"
                               name="educationInfo[${index}].program"
                               placeholder="Программа" required>
                    </div>
                    <div class="col-md-4">
                        <input type="date" class="form-control"
                               name="educationInfo[${index}].startDate" required>
                    </div>
                    <div class="col-md-4">
                        <input type="date" class="form-control"
                               name="educationInfo[${index}].endDate">
                    </div>
                    <div class="col-md-4">
                        <input class="form-control"
                               name="educationInfo[${index}].degree"
                               placeholder="Степень" required>
                    </div>
                </div>
            </div>
        </div>`);
    });

    addExperience?.addEventListener("click", () => {
        const index = experienceIndex++;

        experienceContainer.insertAdjacentHTML("beforeend", `
        <div class="card border mb-4 experience-item">
            <div class="card-body">
                <div class="row g-3">
                    <div class="col-md-6">
                        <input class="form-control"
                               name="workExperienceInfo[${index}].companyName"
                               placeholder="Компания" required>
                    </div>
                    <div class="col-md-6">
                        <input class="form-control"
                               name="workExperienceInfo[${index}].position"
                               placeholder="Должность" required>
                    </div>
                    <div class="col-md-4">
                        <input type="number" class="form-control"
                               name="workExperienceInfo[${index}].years"
                               placeholder="Годы" required>
                    </div>
                    <div class="col-12">
                        <textarea class="form-control"
                                  name="workExperienceInfo[${index}].responsibilities"
                                  placeholder="Обязанности"
                                  rows="3" required></textarea>
                    </div>
                </div>
            </div>
        </div>`);
    });
});
