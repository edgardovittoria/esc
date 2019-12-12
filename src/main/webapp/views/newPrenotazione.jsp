<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/webjars/font-awesome/css/font-awesome.min.css"></link>
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Nuova prenotazione</title>
</head>
<body>
    <div class="container">
        <h1>Vista di prova per la prenotazione</h1>
        <c:if test="${Sport != null}">
        <div class="row">
            <div class="col-xs-1-12">
                <h3>Sport</h3>
            </div>
            <div class="col-xs-1-12">
                <div class="btn-group" role="group" aria-label="Lista sport disponibili">
                    <c:forEach var="sport" items="${Sport}" varStatus="counter">
                        <button type="button" class="btn btn-secondary">${sport.sportDescription}</button>
                    </c:forEach>
                </div>
            </div>
        </div>
        </c:if>
        <c:if test="${Impianti != null}">
        <div class="row">
            <div class="col-xs-1-12">
                <h3>Impianti</h3>
            </div>
            <div class="col-xs-1-12">
                <div class="btn-group" role="group" aria-label="Lista sport disponibili">
                    <c:forEach var="impianto" items="${Impianti}" varStatus="counter">
                        <button type="button" class="btn btn-secondary">
                            <div class="content">${impianto.idImpianto}</div>
                            <c:choose>
                                <c:when test="${impianto.indoor}">
                                        <div class="content">coperto</div>
                                </c:when>
                                <c:otherwise>
                                        <div class="content">scoperto</div>
                                </c:otherwise>
                            </c:choose>
                        </button>
                    </c:forEach>
                </div>
            </div>
        </div>
        </c:if>
    </div>
</body>
</html>
