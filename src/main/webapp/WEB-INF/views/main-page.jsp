<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>ToDo App</title>
    <link rel="stylesheet" href="resources/css/main-page.css"/>
</head>
<body>

<div class="card">
    <div class="header">
        <h1>ToDo List</h1>
        <span class="badge">${numberOfActiveRecords} to do, ${numberOfDoneRecords} done</span>
    </div>

    <div class="filters">
        <button class="filter-btn active">All</button>
        <button class="filter-btn">Active</button>
        <button class="filter-btn">Done</button>
        <button class="apply-btn">Apply</button>
    </div>

    <div class="task-list">
        <c:forEach items="${records}" var="record">
            <div class="task-item">

                <form action="/make-record-done" method="post">
                    <input type="hidden" name="id" value="${record.id}">
                    <button type="submit" class="check-btn ${record.status == 'DONE' ? 'checked' : ''}">
                        <svg class="check-icon" viewBox="0 0 13 10">
                            <path d="M1 5l4 4L12 1"/>
                        </svg>
                    </button>
                </form>

                <span class="task-text ${record.status == 'DONE' ? 'done' : ''}">${record.title}</span>

                <form action="/remove-record" method="post">
                    <input type="hidden" name="id" value="${record.id}">
                    <button type="submit" class="del-btn">×</button>
                </form>

            </div>
        </c:forEach>

    </div>

    <form action="/add-record" method="post" class="add-row">
        <input class="add-input" name="title" placeholder="What needs to be done..." />
        <button class="add-btn">Add Record</button>
    </form>
</div>

<!--<script>
    document.addEventListener('click', (e) => {
        const btn = e.target.closest('.check-btn');
        if (!btn) return;

        const task = btn.closest('.task-item');
        const text = task.querySelector('.task-text');

        const isChecked = btn.classList.toggle('checked');

        task.classList.toggle('done', isChecked);
        text.classList.toggle('done', isChecked);
    });
</script>-->

</body>
</html>