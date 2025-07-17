<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 16.07.2025
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<div class="card shadow-sm">
    <div class="card-header bg-white">
        <h3 class="my-2"><i class="bi bi-file-earmark-plus"></i> ${param.title}</h3>
    </div>
    <div class="card-body">
        <form id="testForm">
            <div class="mb-3">
                <label for="title" class="form-label">Название теста:</label>
                <input type="text" class="form-control" id="title" required>
            </div>

            <div class="mb-3">
                <label for="topic" class="form-label">Тема:</label>
                <input type="text" class="form-control" id="topic">
            </div>

            <div id="questionsContainer" class="mb-4"></div>

            <div class="d-flex justify-content-between">
                <button type="button" class="btn btn-primary" onclick="addQuestion()">
                    <i class="bi bi-plus-circle"></i> Добавить вопрос
                </button>
                <button type="submit" class="btn btn-success">
                    <i class="bi bi-save"></i> Сохранить тест
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    // Инициализация
    var questionCounter = 0;
    var answerCounters = {};

    // Добавление первого вопроса при загрузке
    window.onload = function() {
        addQuestion();
    };

    // Функции для работы с вопросами и ответами
    function addQuestion() {
        questionCounter++;
        answerCounters[questionCounter] = 0;

        var container = document.getElementById('questionsContainer');
        var questionDiv = document.createElement('div');
        questionDiv.className = 'question-block';
        questionDiv.id = 'question-' + questionCounter;

        questionDiv.innerHTML = `
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h5 class="mb-0">Вопрос \${questionCounter}</h5>
                <button type="button" class="btn btn-sm btn-outline-danger"
                        onclick="removeQuestion(${questionCounter})">
                    <i class="bi bi-trash"></i> Удалить
                </button>
            </div>
            <textarea class="form-control mb-3" rows="3" required
                      placeholder="Текст вопроса"></textarea>
            <div id="answers-\${questionCounter}" class="mb-3"></div>
            <button type="button" class="btn btn-sm btn-outline-primary"
                    onclick="addAnswer(\${questionCounter})">
                <i class="bi bi-plus-circle"></i> Добавить ответ
            </button>
        `;

        container.appendChild(questionDiv);
        addAnswer(questionCounter);
    }

    function addAnswer(questionId) {
        answerCounters[questionId] = (answerCounters[questionId] || 0) + 1;
        var answerId = answerCounters[questionId];
        var container = document.getElementById('answers-' + questionId);
        var answerDiv = document.createElement('div');
        answerDiv.className = 'answer-block d-flex align-items-center mb-2';
        answerDiv.id = 'answer-' + questionId + '-' + answerId;

        answerDiv.innerHTML = `
            <div class="form-check me-3">
                <input class="form-check-input" type="checkbox"
                       id="answer-\${questionId}-\${answerId}-correct">
            </div>
            <input type="text" class="form-control me-3"
                   placeholder="Текст ответа" required>
            <button type="button" class="btn btn-sm btn-outline-danger"
                    onclick="removeAnswer(\${questionId}, \${answerId})">
                <i class="bi bi-x-circle"></i>
            </button>
        `;

        container.appendChild(answerDiv);

        // Подсветка правильного ответа
        var checkbox = answerDiv.querySelector('input[type="checkbox"]');
        checkbox.onchange = function() {
            answerDiv.classList.toggle('correct-answer', this.checked);
        };
    }

    // Обработка формы
    document.getElementById('testForm').onsubmit = function(e) {
        e.preventDefault();

        var formData = {
            title: document.getElementById('title').value,
            topic: document.getElementById('topic').value,
            questions: []
        };

        var isValid = true;

        // Сбор данных вопросов
        document.querySelectorAll('.question-block').forEach(function(questionDiv, qIndex) {
            var questionText = questionDiv.querySelector('textarea').value;
            var questionData = {
                text: questionText,
                answers: []
            };

            // Проверка наличия текста вопроса
            if (!questionText.trim()) {
                alert('Вопрос ' + (qIndex + 1) + ': текст вопроса не может быть пустым');
                isValid = false;
                return;
            }

            // Сбор данных ответов
            var hasCorrectAnswer = false;
            questionDiv.querySelectorAll('.answer-block').forEach(function(answerDiv, aIndex) {
                var answerText = answerDiv.querySelector('input[type="text"]').value;
                var isCorrect = answerDiv.querySelector('input[type="checkbox"]').checked;

                if (!answerText.trim()) {
                    alert('Вопрос ' + (qIndex + 1) + ', ответ ' + (aIndex + 1) + ': текст ответа не может быть пустым');
                    isValid = false;
                    return;
                }

                if (isCorrect) hasCorrectAnswer = true;

                questionData.answers.push({
                    text: answerText,
                    isCorrect: isCorrect
                });
            });

            if (!hasCorrectAnswer) {
                alert('Вопрос ' + (qIndex + 1) + ' должен иметь хотя бы один правильный ответ');
                isValid = false;
                return;
            }

            formData.questions.push(questionData);
        });

        if (!isValid) return;

        // Отправка данных
        var xhr = new XMLHttpRequest();
        xhr.open('POST', '${pageContext.request.contextPath}/admin/test/create', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onload = function() {
            if (xhr.status === 201) {
                alert('Тест успешно сохранён!');
                window.location.href = '${pageContext.request.contextPath}/admin';
            } else {
                alert('Ошибка: ' + xhr.responseText);
            }
        };
        xhr.send(JSON.stringify(formData));
    };
</script>
