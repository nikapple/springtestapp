<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Welcome User</title>
   </head>

   <body>
      <h2>Logged in! as ${user.firstName}</h2>
   </body>
   
</html>