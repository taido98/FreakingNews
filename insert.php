<?php
include_once 'dbConnect.php';    

$email =$_POST['username'];
$name =$_POST['name'];
$url_avatar = $_POST['url'];
if (!$connect) {
    die('Khong the ket noi den may chu: ' . mysqli_error($connect));
    exit();
}
mysqli_set_charset($connect,'utf8');
// $result=$connect->query("SELECT * FROM `users` WHERE 'email'='$email'");
$result = mysqli_query($connect,"SELECT * FROM `users` WHERE email='$email'" );
if($result->num_rows == 0){
    $query = "INSERT INTO users VALUES(null, '$email', '$name', '$url_avatar')";
    if(mysqli_query($connect, $query)){
        echo "success";
    }
    else{
        echo "error @@@";
    }	
}

?>