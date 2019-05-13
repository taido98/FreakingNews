<?php

    include_once "dbconnect.php";

	$response = "one err";

    if($_SERVER['REQUEST_METHOD']=='POST') {
		$imageName = $_POST['imageName'];	
		$imageCode = $_POST['imageCode'];
		$idUser = (int)$_POST["idUser"];
		$idTopic = (int)$_POST["idTopic"];
		$content = $_POST["content"];	
		
		// Tạo một thư mục chứa ảnh
		// imaName là tên ảnh, để không trùng các bạn có thể add thêm ngày tháng cho nó
		$path = "upload/$imageName";
		// Đường dẫn
		$actualpath = "http://localhost/FreakingNews/$path";
		$json = '[{"url":"'.$actualpath.'"}]';
		$sql = "INSERT INTO posts(idUser, idTopic, content, url_image) VALUES($idUser,$idTopic,'$content','$json')";	
		if($connect->query($sql)){
			// đẩy data vào path
			file_put_contents($path,base64_decode($imageCode));
			$response = "Success";
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