function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    if (sidebar.style.left === '0px') {
        sidebar.style.left = '-200px';
    } else {
        sidebar.style.left = '0px';
    }
}

function openForm() {
    document.getElementById('questionModal').style.display = 'block';
}

function closeForm() {
    document.getElementById('questionModal').style.display = 'none';
}

function toggleTheme() {
    document.body.classList.toggle('light-mode');
}

async function fetchQuestions() {
    const response = await fetch('/api/questions');
    const questions = await response.json();
    displayQuestions(questions);
}

async function fetchQuestionsByTag(tag) {
    const response = await fetch(`/api/questions/category?category=${tag}`);
    const questions = await response.json();
    displayQuestions(questions);
}

function displayQuestions(questions) {
    const questionsDiv = document.getElementById('questions');
    questionsDiv.innerHTML = '';
    questions.forEach(question => {
        const questionDiv = document.createElement('div');
        questionDiv.className = 'question';
        questionDiv.innerHTML = `
            <h3>${question.content}</h3>
            <p>Category: ${question.category}</p>
            <input type="text" id="answer-${question.id}" placeholder="Your answer">
            <button onclick="checkAnswer(${question.id}, '${question.solution}')">Submit Answer</button>
            <button onclick="editQuestion(${question.id}, '${question.content}', '${question.solution}', '${question.category}')">Edit</button>
            <button onclick="deleteQuestion(${question.id})">Delete</button>
            <div class="feedback" id="feedback-${question.id}"></div>
        `;
        questionsDiv.appendChild(questionDiv);
    });
}

function editQuestion(id, content, solution, category) {
    document.getElementById('questionId').value = id;
    document.getElementById('content').value = content;
    document.getElementById('solution').value = solution;
    document.getElementById('category').value = category;
    openForm();
}

async function submitForm() {
    const id = document.getElementById('questionId').value;
    const content = document.getElementById('content').value;
    const solution = document.getElementById('solution').value;
    const category = document.getElementById('category').value;

    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/questions/${id}` : '/api/questions';

    const response = await fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content, solution, category })
    });

    if (response.ok) {
        document.getElementById('questionId').value = '';
        document.getElementById('content').value = '';
        document.getElementById('solution').value = '';
        document.getElementById('category').value = 'Mechanics';
        closeForm();
        fetchQuestions();
    } else {
        alert('Failed to submit question');
    }
}

async function deleteQuestion(id) {
    const response = await fetch(`/api/questions/${id}`, {
        method: 'DELETE'
    });

    if (response.ok) {
        fetchQuestions();
    } else {
        alert('Failed to delete question');
    }
}

function checkAnswer(id, correctSolution) {
    const userAnswer = document.getElementById(`answer-${id}`).value;
    const feedbackDiv = document.getElementById(`feedback-${id}`);
    if (userAnswer.trim().toLowerCase() === correctSolution.trim().toLowerCase()) {
        feedbackDiv.innerHTML = 'Correct!';
        feedbackDiv.style.color = 'green';
    } else {
        feedbackDiv.innerHTML = `Incorrect. The correct answer is: ${correctSolution}`;
        feedbackDiv.style.color = 'red';
    }
}
async function validateKey() {
    const key = prompt("Enter the key to add a question:");
    if (!key) {
        alert("Key is required to add a question.");
        return false;
    }

    const response = await fetch(`/api/questions/validateKey?key=${key}`);
    const isValid = await response.json();

    if (!isValid) {
        alert("Incorrect key.");
        return false;
    }

    return true;
}

async function openFormWithKeyValidation() {
    const isValid = await validateKey();
    if (isValid) {
        openForm();
    }
}

function redirectToMCQs() {
    window.location.href = 'mcqs.html';
}

function redirectToHome() {
    window.location.href = 'index.html';
}

document.getElementById('addQuestionLink').onclick = openFormWithKeyValidation;

window.onload = fetchQuestions;