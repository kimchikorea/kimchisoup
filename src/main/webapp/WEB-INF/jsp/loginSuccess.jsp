<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>�α��� ����</title>
</head>
<body>
    <h1>�α��� ����!!</h1>
    <hr>
    <p>
        <span sec:authentication="name"></span>�� ȯ���մϴ�~
    </p>
    <a th:href="@{'/'}">�������� �̵�</a>
</body>
</html>
