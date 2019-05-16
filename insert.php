<?php
include_once 'dbConnect.php';    
session_start();
$email =$_POST['email'];
$name =$_POST['name'];
$url_avatar = $_POST['url'];
// $idUser=$_GET['idUser'];
$idUser=8;

$result = mysqli_query($connect,"SELECT `id` FROM `users` WHERE email='$email' AND url_avatar ='$url_avatar'");
if($result->num_rows == 0){
    $query = "INSERT INTO users VALUES(null, '$email', '$name', '$url_avatar')";
    if(mysqli_query($connect, $query)){
        $result = mysqli_query($connect,"SELECT `id` FROM `users` WHERE email='$email' AND url_avatar ='$url_avatar'" );
        while($rows = $result->fetch_assoc()){
            $idUser = $rows["id"];
        }
        echo $idUser;
    }
    else{
        echo "error @@@";
    }

}
else{
    while($rows = $result->fetch_assoc()){
        $idUser = $rows["id"];
    }
    echo $idUser;
}

$connect->close();

?>