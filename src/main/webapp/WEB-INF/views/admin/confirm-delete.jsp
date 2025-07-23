<%--
  Created by IntelliJ IDEA.
  User: Сергей
  Date: 23.07.2025
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<div id="deleteModal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:1000;">
  <div style="background:white; width:400px; margin:100px auto; padding:20px; border-radius:5px;">
    <h3>Подтверждение удаления</h3>
    <p>Вы уверены, что хотите удалить тест "<span id="testTitleToDelete"></span>"?</p>
    <div style="text-align:right; margin-top:20px;">
      <button id="confirmDeleteBtn" class="btn btn-danger">Удалить</button>
      <button id="cancelDeleteBtn" class="btn btn-secondary" style="margin-left:10px;">Отмена</button>
    </div>
  </div>
</div>

<script>
  $(document).ready(function() {
    // Обработчик для кнопки "Удалить" в списке
    $('a[href^="/admin/test/delete"]').click(function(e) {
      e.preventDefault();
      const testId = $(this).attr('data-test-id');
      const testTitle = $(this).attr('data-test-title');

      $('#testTitleToDelete').text(testTitle);
      $('#deleteModal').show();

      $('#confirmDeleteBtn').off('click').on('click', function() {
        deleteTest(testId);
      });
    });

    $('#cancelDeleteBtn').click(function() {
      $('#deleteModal').hide();
    });

    function deleteTest(testId) {
      $.ajax({
        url: '/admin/test/delete',
        method: 'POST',
        data: { id: testId },
        success: function(response) {
          if(response.success) {
            window.location.href = response.redirectUrl;
          }
        },
        error: function(xhr) {
          $('#deleteModal').hide();
          let errorMsg = 'Ошибка при удалении';
          if(xhr.status === 404) {
            errorMsg = 'Тест не найден';
          } else if(xhr.status === 400) {
            errorMsg = 'Некорректный запрос';
          }
          alert(errorMsg);
        }
      });
    }
  });
</script>
