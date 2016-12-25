<%--
  Created by IntelliJ IDEA.
  User: NATTARAT
  Date: 2016-12-23
  Time: 9:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script>
      <script>
      <![CDATA[
      var currentTab = 0;
      $(function () {
        $("#tabs").tabs({
          select: function (e, i) {
            currentTab = i.index;
          }
        });
      });

      var runningEducationId = 0;
      function addEducationRow(x){
        var html = '<tr>';
        html += '    <td>';
        html +=         '        <select name="educationDegree['+runningEducationId+']">';
        html +=         '           <option value="Bachelor">ปริญญาตรี</option>';
        html +=         '           <option value="Master">ปริญญาโท</option>';
        html +=         '           <option value="PhD">ปริญญาเอก</option>';
        html +=         '        </select>';
        html +=         '    </td>';
        html +=         '    <td><input type="text" name="educationField['+runningEducationId+']" value=""/></td>';
        html +=         '    <td><input type="text" name="educationUniversity['+runningEducationId+']" value=""/></td>';
        html +=        '    <td><input type="text" name="universityGraduationYear['+runningEducationId+']" value=""/></td>';
        html +=        '    <td><button type="button" id="delete-ducation" onclick="removeEducationRow(this);">-</button></td>';
        html +=        '</tr>';
        runningEducationId = runningEducationId +1;
        $('#educationTable tr:last').after(html);
      }

      function removeEducationRow(x){
        var row_index = x.parentNode.parentNode.rowIndex;
        $('#educationTable > thead > tr').eq(row_index).remove();
      }

      $(function() {
        $("#_bdateString_id").datepicker({
          changeMonth: true,
          changeYear: true,
          dateFormat: 'dd M yy',
          yearRange: "${minyear}}:${maxyear}",
          isBuddhist: true,
          dayNames: ['อาทิตย์','จันทร์','อังคาร','พุธ','พฤหัสบดี','ศุกร์','เสาร์'],
          dayNamesMin: ['อา.','จ.','อ.','พ.','พฤ.','ศ.','ส.'],
          monthNames: ['มกราคม','กุมภาพันธ์','มีนาคม','เมษายน','พฤษภาคม','มิถุนายน','กรกฎาคม','สิงหาคม','กันยายน','ตุลาคม','พฤศจิกายน','ธันวาคม'],
          monthNamesShort: ['ม.ค.','ก.พ.','มี.ค.','เม.ย.','พ.ค.','มิ.ย.','ก.ค.','ส.ค.','ก.ย.','ต.ค.','พ.ย.','ธ.ค.'],
          defaultDate: "-20y"
        });
      });
      ]]>
    </script>
</head>
<body>
  <!-- education -->
  <spring:message code="label_com_cma_std_profile_print_header6" var="header6"/>
  <div id="fieldHeader">
    ${header6}
  </div>
  <spring:message code="label_com_cma_std_profile_print_educationLevel" var="educationLevel"/>
  <spring:message code="label_com_cma_std_profile_print_education1" var="education1"/>
  <spring:message code="label_com_cma_std_profile_print_education2" var="education2"/>
  <spring:message code="label_com_cma_std_profile_print_education3" var="education3"/>
  <table id="educationTable">
    <thead>
    <tr>
      <th>${educationLevel}</th>
      <th>${education1}</th>
      <th>${education2}</th>
      <th>${education3}</th>
      <th></th>
    </tr>
    </thead>
  </table>
  <div>
    <button type="button" class="btn btn-success" onclick="addEducationRow(this);">Add</button>
  </div>
  <br/>
</body>
</html>
