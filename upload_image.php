<?php

    include_once "dbconnect.php";

    $response;

    if($_SERVER['REQUEST_METHOD']=='POST') {
		$imageName = $_POST['imageName'];	
		
		$imageCode = $_POST['imageCode'];	
		
		// Tạo một thư mục chứa ảnh
		// imaName là tên ảnh, để không trùng các bạn có thể add thêm ngày tháng cho nó
		$path = "upload/$imageName";
		// Đường dẫn
		$actualpath = "http://localhost/FreakingNews/$path";
		 	
		$sql = "INSERT INTO image(url) VALUES ('$actualpath')";	
		if($connect->query($sql)){
			// đẩy data vào path
			file_put_contents($path,base64_decode($imageCode));
			$response = $imageName ." va ".$imageCode;
        }
        else
            die("Error sql: "+$connect->error);
		mysqli_close($connect);
	}
	else{
		$response = "POST Failed";
	}
	// echoing response
    echo $response;
?>