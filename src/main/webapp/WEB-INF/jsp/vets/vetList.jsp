<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="vets">
    <h2 id="veterinarians">Veterinarians</h2>

    <div class="row">
        <table id="vetsTable" class="table table-striped" aria-describedby="veterinarians">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Specialties</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${vets.vetList}" var="vet">
                <tr>
                    <td>
                        <spring:url value="/vets/{vetId}" var="vetUrl">
                            <spring:param name="vetId" value="${vet.id}"/>
                        </spring:url>
                        <a href="${fn:escapeXml(vetUrl)}">
                            <c:out value="${vet.firstName} ${vet.lastName}"/>
                        </a>
                    </td>
                    <td>
                        <c:forEach var="specialty" items="${vet.specialties}">
                            <c:out value="${specialty.name} "/>
                        </c:forEach>
                        <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="row">
        <div class="col-md-2">
            <a href="<spring:url value="/vets.xml" htmlEscape="true" />">View as XML</a>
        </div>
        <div class="col-md-2">
            <a href="<spring:url value="/vets.json" htmlEscape="true" />">View as JSON</a>
        </div>
    </div>
</petclinic:layout>
