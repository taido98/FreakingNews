<?php

    include_once "dbconnect.php";

	$response = "one err";

    if($_SERVER['REQUEST_METHOD']=='POST') {
		// $json = json_decode($_POST['jsonCode'],true);
		$size = (int)$_POST["size"];
		$idUser = (int)$_POST["idUser"];
		$idTopic = (int)$_POST["idTopic"];
		$content = $_POST["content"];	
		$url = "";

		for($i = 0; $i < $size; $i++){
			// Tạo một thư mục chứa ảnh
			// imaName là tên ảnh, để không trùng các bạn có thể add thêm ngày tháng cho nó
			$path = "upload/".$_POST["imgName".$i];
			$imageCode = $_POST["imgCode".$i];
			// Đường dẫn
			$actualpath = "http://localhost/FreakingNews/$path";
			$url = $url.'{"url":"'.$actualpath.'"},';
			// đẩy data vào path
			file_put_contents($path,base64_decode($imageCode));	
		}
		$url = '['.substr($url,0,strlen($url)-1).']';
		$sql = "INSERT INTO posts(idUser, idTopic, content, url_image) VALUES($idUser,$idTopic,'$content','$url')";	
		if($connect->query($sql)){
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