<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<style>
.container {padding: 2rem 0rem;}
@media (min-width: 576px){
  #modalLogin.modal-dialog {max-width: 400px;.modal-content {padding: 1rem;}}
}
.form-title {margin: -2rem 0rem 2rem;}
.btn-round {border-radius: 3rem;}
.delimiter {padding: 1rem;}
.signup-section {padding: 0.3rem 0rem;}
.fade{-webkit-transition-property: none;-moz-transition-property: none;-o-transition-property: none;transition-property: none;transition:none;}
.fade#modalLogin {background : #6c757d;}
</style>

<script>

	$.ajaxSetup({
		beforeSend : function (xhr) {
			checkHeader(xhr);
		}
	});

	var checkHeader = function(req){
	};
	
</script>
