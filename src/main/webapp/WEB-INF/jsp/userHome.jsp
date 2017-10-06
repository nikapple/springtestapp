<%@page contentType = "text/html;charset = UTF-8" language = "java" %>
<%@page isELIgnored = "false" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
      <title>Welcome User</title>
      <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
      <script type="text/javascript">
      $(document).ready(function(){
    	  $('[name="book"]').click(function(){
    	    	$.ajax({                                      
    	    	      url: '/user-registration/book?bookName='+$(this).val(),              
    	    	      type: "get",
    	    	      success: function(result){
    	    	    	  console.log(result);
    	    	    	  if(result == true)
    	    	    	  	$(this).attr("disabled", true);
    	    	      }
    	    	   });
    	    });
    	});
      </script>
   </head>

   <body>
      <h2>Logged in! as ${user.firstName}</h2>
      <table>
		   <tr>
		    <th>Topic Name</th>
		    <th>Book Name</th> 
		    <th>Book Author</th>
		    <th>Completed</th>
		  </tr>
      <c:forEach items="${bookList}" var="book">
		    <tr>
		        <td><c:out value="${book.topic}"/></td>
		        <td><c:out value="${book.name}"/></td>
		        <td><c:out value="${book.author}"/></td>
		        <td><input type="checkbox" name="book" id="${book.name}" value="${book.name}"><br></td>
		    </tr>
		</c:forEach>
		</table>
   </body>
   
   
</html>