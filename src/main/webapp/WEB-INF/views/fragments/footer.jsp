<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>


</div>
</body>
<section class="fixed-bottom">
    <!-- Footer -->
    <footer class="text-center text-white bg-black">
        <!-- Grid container -->
        <div class="container p-4 pb-0">
            <!-- Section: CTA -->
            <section class="">

                <p class="d-flex justify-content-center align-items-center text-success">
                    <sec:authorize access="isAnonymous()">
                        <span class="me-3">Register for free:</span>
                        <a class="btn btn-link btn-outline-light btn-rounded text-danger"
                           href="<c:url value='/registration' />">Sign up!</a>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        Zalogowany jako: <sec:authentication
                            property="principal.username"/>
                    </sec:authorize>
                </p>


            </section>
            <!-- Section: CTA -->
        </div>
        <!-- Grid container -->

        <!-- Copyright -->
        <div class="text-center p-3 bg-danger">
            © 2022 Copyright: Kapuśniak Tomasz
        </div>
        <!-- Copyright -->
    </footer>
    <!-- Footer -->
</section>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</html>
