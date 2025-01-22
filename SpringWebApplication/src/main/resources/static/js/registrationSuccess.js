function showSuccessMessage() {
    alert("Вы успешно зарегистрированы! Пожалуйста, войдите.");
    setTimeout(function() {
        window.location.href = "/login";
    }, 1000);
}

// Функция для проверки совпадения паролей
function validatePasswords() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("password2").value;

    if (password !== confirmPassword) {
        alert("Пароли не совпадают! Пожалуйста, повторите попытку.");
        return false; // Отменяем отправку формы
    }
    return true; // Если пароли совпадают, форма будет отправлена
}