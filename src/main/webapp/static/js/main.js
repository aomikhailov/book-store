document.addEventListener("DOMContentLoaded", function () {
    // Слушаем клики по ссылкам с классом "session"
    document.querySelectorAll("a.session").forEach(function (link) {
        link.addEventListener("click", function (event) {
            event.preventDefault(); // Останавливаем стандартное поведение ссылки
            const sessionId = this.getAttribute("id"); // Получаем значение атрибута id

            if (sessionId) {
                // Устанавливаем cookie SESSION_ID с временем жизни 1 час (3600 секунд)
                document.cookie = `SESSION_ID=${sessionId}; path=/; max-age=3600`;

                // Перезагружаем страницу
                location.reload();
            }
        });
    });
});