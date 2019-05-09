<?php
include_once 'dbConnect.php';    
session_start();
$id_User = (int) $_SESSION["id_User"];
// $id_User = 2;
$title =$_POST['title_news'];
$link =$_POST['link_news'];

//Cai nay co roi

$result = mysqli_query($connect,"SELECT `id` FROM `news_save` WHERE link = '$link' AND idUser = '$id_User'" );
/* Dong tren nay la sao 
Dung de set xem link ddc luu chua nhuwng thieu dk , chưa đủ?
 */
if($result->num_rows == 0){
    $query = "INSERT INTO news_save VALUES(null, $id_User, '$title', '$link')";
    if(mysqli_query($connect, $query)){
        echo "success";
    }
    else{
        echo $id_User;
    }
}
else{
    echo "Tin đã lưu !";
}

$connect->close();
?>