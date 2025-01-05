<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Message</title>
</head>
<body>
<h1>Message Display</h1>

<p>Your message is: <strong>${message.text}</strong></p>

<form action="${pageContext.request.contextPath}/message" method="get">
    <label for="text">Enter Message:</label>
    <input type="text" id="text" name="text" />
    <button type="submit">Submit</button>
</form>

</body>
</html>
