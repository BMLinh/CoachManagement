<%-- 
    Document   : index
    Created on : Mar 15, 2022, 10:11:56 PM
    Author     : Asus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-center text-danger">CoachManagementApp</h1>

<c:if test="${currentUser != null}">
    ${currentUser.phone} - ${currentUser.fullname}
</c:if>
