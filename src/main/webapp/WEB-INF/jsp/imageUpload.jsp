<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Image Upload</title>
    <script type="text/javascript">
      function previewImage(event) {
        let reader = new FileReader();
        reader.onload = function(){
          let output = document.getElementById('preview');
          output.src = reader.result;
        };
        reader.readAsDataURL(event.target.files[0]);
      }
    </script>
</head>
<body>
<h1>Upload an Image</h1>

<form action="/uploadImage" method="post" enctype="multipart/form-data">
    <label for="file">Select image:</label>
    <input type="file" id="file" name="imageFile" accept="image/*" onchange="previewImage(event)" required />
    <br><br>
    <img id="preview" src="#" alt="Image Preview" style="display:none;" width="300" />
    <br><br>
    <button type="submit">Upload Image</button>
</form>

<script type="text/javascript">
  // Show image preview only when an image is selected
  document.getElementById('file').addEventListener('change', function() {
    document.getElementById('preview').style.display = 'block';
  });
</script>
</body>
</html>
