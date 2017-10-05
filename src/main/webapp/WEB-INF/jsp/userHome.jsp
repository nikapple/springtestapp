<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
      <title>Welcome User</title>
   </head>

   <body>
      <h2>Logged in! as ${user.firstName}</h2>
      
      <c:forEach items="${bookList}" var="book">
		    <tr>
		        <td>Topic Name: <c:out value="${book.topic}"/></td>
		        <td>Book Name: <c:out value="${book.name}"/></td>
		        <td>Book Author: <c:out value="${book.author}"/></td>
		    </tr>
		</c:forEach>
   </body>
   
   
</html>