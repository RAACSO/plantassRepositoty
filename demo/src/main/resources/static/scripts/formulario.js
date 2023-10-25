let currentStep = 1;

function updateProgress() {
    const totalSteps = 4; // Cambia esto al número total de pasos
    const progress = (currentStep - 1) / (totalSteps - 1) * 100;
    document.getElementById('progress').style.width = progress + '%';
}

function nextStep(step) {
    currentStep += step;
    if (currentStep > 4) currentStep = 4; // Cambia esto al número total de pasos
    showStep(currentStep);
    updateProgress();
}

function prevStep(step) {
    currentStep -= step;
    if (currentStep < 1) currentStep = 1;
    showStep(currentStep);
    updateProgress();
}

function showStep(step) {
    const steps = document.querySelectorAll('.step');
    steps.forEach((s, index) => {
        if (index === step - 1) {
            s.style.display = 'block';
        } else {
            s.style.display = 'none';
        }
    });
}

document.getElementById('multipasos-form').addEventListener('submit', function (event) {
    event.preventDefault();
    alert('Formulario enviado con éxito.');
});

showStep(currentStep);
updateProgress();
