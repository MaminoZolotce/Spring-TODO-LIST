<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
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

    <form action="/home" method="get" class="filter-form">
        <div class="filters">
            <div class="filter-form_input">
                <input type="radio" id="filter-form__status_all"
                       name="filter" value="all"
                ${empty param.filter or (fn:toLowerCase(param.filter) != 'done' and fn:toLowerCase(param.filter) != 'active') ? 'checked' : ''}>
                <label for="filter-form__status_all" class="filter-btn">All</label>
            </div>

            <div class="filter-form_input">
                <input type="radio" id="filter-form__status_active"
                       name="filter" value="active"
                ${fn:toLowerCase(param.filter) == 'active' ? 'checked' : ''}>
                <label for="filter-form__status_active" class="filter-btn">Active</label>
            </div>

            <div class="filter-form_input">
                <input type="radio" id="filter-form__status_done"
                       name="filter" value="done"
                ${fn:toLowerCase(param.filter) == 'done' ? 'checked' : ''}>
                <label for="filter-form__status_done" class="filter-btn">Done</label>
            </div>

            <button type="submit" class="apply-btn">Apply</button>
        </div>
    </form>

    <div class="task-list">
        <c:choose>
            <c:when test="${not empty records}">
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
                            <input type="hidden" name="filter" value="${fn:toLowerCase(param.filter)}">
                            <input type="hidden" name="id" value="${record.id}">
                            <button type="submit" class="del-btn">×</button>
                        </form>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${fn:toLowerCase(param.filter == 'active')}">
                        <div class="task-item">
                            <span class="task-text">There are no active tasks yet</span>
                        </div>
                    </c:when>
                    <c:when test="${fn:toLowerCase(param.filter == 'done')}">
                        <div class="task-item">
                            <span class="task-text">There are no done tasks yet</span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="task-item">
                            <span class="task-text">There are no tasks yet, add new one</span>
                        </div>
                    </c:otherwise>

                </c:choose>

            </c:otherwise>
        </c:choose>
    </div>

    <form action="/add-record" method="post" class="add-row">
        <input type="hidden" name="filter" value="${fn:toLowerCase(param.filter)}">
        <input class="add-input" name="title" placeholder="What needs to be done..."/>
        <button class="add-btn">Add Record</button>
    </form>
</div>

</body>
</html>